/**
 * @version 1.1  2015年4月5日
 */
package com.louisgeek.httplib.util;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.louisgeek.httplib.bean.UploadBase64ImgsBean;
import com.louisgeek.httplib.bean.UploadBase64ImgsWithWeatherBean;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * ImageUtil.java
 * @author louisgeek
 * 2015年2月5日上午11:05:50 
 */
public  class ImageUtil {

	/**
	 * 尽量不要使用setImageBitmap或setImageResource或BitmapFactory.decodeResource来设置一张大图，
    因为这些函数在完成decode后，最终都是通过java层的createBitmap来完成的，需要消耗更多内存。
    因此，改用先通过BitmapFactory.decodeStream方法，创建出一个bitmap，再将其设为ImageView的 source，
    decodeStream最大的秘密在于其直接调用JNI>>nativeDecodeAsset()来完成decode，
    无需再使用java层的createBitmap，从而节省了java层的空间。
    如果在读取时加上图片的Config参数，可以跟有效减少加载的内存，从而跟有效阻止抛out of Memory异常
    另外，decodeStream直接拿的图片来读取字节码了， 不会根据机器的各种分辨率来自动适应，
    使用了decodeStream之后，需要在hdpi和mdpi，ldpi中配置相应的图片资源，
    否则在不同分辨率机器上都是同样大小（像素点数量），显示出来的大小就不对了。
	 */
	/*	//从资源中获取Bitmap
	 Resources res = getResources();
	Bitmap bmp = BitmapFactory.decodeResource(res, R.drawable.icon);*/
	/**
	 * 从资源中获取Bitmap  解决内存溢出   OOM溢出处理了 有时候还是会溢出
	 * @param resources  getResources()
	 * @param id   R.drawable.icon
	 * @return
	 */
	 public static Bitmap zoomBitmapWithWidthHeightFromResources(Resources resources,int id,int width,int height) {

	         BitmapFactory.Options opts = null;
	         if (width > 0 && height > 0) {
	             opts = new BitmapFactory.Options();
	             opts.inJustDecodeBounds = true;
	             BitmapFactory.decodeResource(resources, id, opts);
	             // 计算图片缩放比例
	             final int minSideLength = Math.min(width, height);
	             opts.inSampleSize = computeSampleSize(opts, minSideLength,
	                     width * height);
	             opts.inJustDecodeBounds = false;
	             opts.inInputShareable = true;
	             opts.inPurgeable = true;
	         }
	         try {
	             return BitmapFactory.decodeResource(resources, id, opts);
	         } catch (OutOfMemoryError e) {
	             e.printStackTrace();
	         }
	         return null;
		    }

	
	/**
	 * Bitmap → byte[]
	 * @param bm
	 * @return
	 */
	 public static byte[] Bitmap2Bytes(Bitmap bm) {
		        ByteArrayOutputStream baos = new ByteArrayOutputStream();
		         bm.compress(CompressFormat.PNG, 100, baos);
		          return baos.toByteArray();
		    }
	 /**
	  * byte[] → Bitmap
	  * @param b
	  * @return
	  */
	 public static Bitmap Bytes2Bimap(byte[] b) {
	        if (b.length != 0) {
	            return BitmapFactory.decodeByteArray(b, 0, b.length);
	        } else {
	            return null;
	        }
	    }
	 /**
	  * Bitmap缩放  没有做OOM溢出处理
	  * @param bitmap
	   * @param width  
	  * @param height  
	  * @return
	  */
	 public  static Bitmap zoomBitmapWithWidthHeight(Bitmap bitmap, int width, int height) {
	        int w = bitmap.getWidth();
	        int h = bitmap.getHeight();
	        Matrix matrix = new Matrix();
	        float scaleWidth = ((float) width / w);
	        float scaleHeight = ((float) height / h);
	        matrix.postScale(scaleWidth, scaleHeight);
	        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
	        return newbmp;
	    }
	 /**
	  * Bitmap设置缩放大小对图片作处理  从File   OOM溢出处理了
	  * <br>使用这个方法解决加载大图片内存溢出问题  
	  * @param dst
	  * @param width  
	  * @param height  
	  * @return
	  */
	 public static Bitmap zoomBitmapFromFileWithWidthHeight(File dst, int width, int height) {
	     if (null != dst && dst.exists()) {
	         BitmapFactory.Options opts = null;
	         if (width > 0 && height > 0) {
	             opts = new BitmapFactory.Options();
	             opts.inJustDecodeBounds = true;
	             BitmapFactory.decodeFile(dst.getPath(), opts);
	             // 计算图片缩放比例
	             final int minSideLength = Math.min(width, height);
	             opts.inSampleSize = computeSampleSize(opts, minSideLength,
	                     width * height);
	             opts.inJustDecodeBounds = false;
	             opts.inInputShareable = true;
	             opts.inPurgeable = true;
	         }
	         try {
	             return BitmapFactory.decodeFile(dst.getPath(), opts);
	         } catch (OutOfMemoryError e) {
	             e.printStackTrace();
	         }
	     }
	     return null;
	 }
	//计算样本大小
	 private static int computeSampleSize(BitmapFactory.Options options,  
	         int minSideLength, int maxNumOfPixels) {  
	     int initialSize = computeInitialSampleSize(options, minSideLength,  
	             maxNumOfPixels);  
	   
	    int roundedSize;  
	     if (initialSize <= 8) {  
	         roundedSize = 1;  
	         while (roundedSize < initialSize) {  
	             roundedSize <<= 1;  
	         }  
	     } else {  
	         roundedSize = (initialSize + 7) / 8 * 8;  
	     }  
	   
	    return roundedSize;  
	 } 
	 //计算初始化样本大小
	 private static int computeInitialSampleSize(BitmapFactory.Options options,  
	         int minSideLength, int maxNumOfPixels) {  
	     double w = options.outWidth;  
	     double h = options.outHeight;  
	   
	    int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math  
	             .sqrt(w * h / maxNumOfPixels));  
	     int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(Math  
	             .floor(w / minSideLength), Math.floor(h / minSideLength));  
	   
	    if (upperBound < lowerBound) {  
	         // return the larger one when there is no overlapping zone.  
	         return lowerBound;  
	     }  
	   
	    if ((maxNumOfPixels == -1) && (minSideLength == -1)) {  
	         return 1;  
	     } else if (minSideLength == -1) {  
	         return lowerBound;  
	     } else {  
	         return upperBound;  
	     }  
	 }  
	 /**
	  * 从Uri获得文件路径
	  * @param uri
	  * @param activity
	  * @return
	  */
	 public static String getImagePathByUri(Uri uri,Activity activity)
	   {
	       // can post image
	       String [] proj={MediaStore.Images.Media.DATA};
	       Cursor cursor = activity.managedQuery( uri,
	                       proj,                 // Which columns to return
	                       null,       // WHERE clause; which rows to return (all rows)
	                       null,       // WHERE clause selection arguments (none)
	                       null);                 // Order-by clause (ascending by name)
	      
	       int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	       cursor.moveToFirst();
	        
	       return cursor.getString(column_index);
	   }
	 /**
	 * 从文件路径获得Uri
	 */
	public static Uri getUriByImagePath(String picPath,Activity activity) {
		// TODO 自动生成的方法存根
        Uri mUri = Uri.parse("content://media/external/images/media"); 
        Uri mImageUri = null;

        Cursor cursor = activity.managedQuery(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null,
                null, MediaStore.Images.Media.DEFAULT_SORT_ORDER);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            String data = cursor.getString(cursor
                    .getColumnIndex(MediaStore.MediaColumns.DATA));
            if (picPath.equals(data)) {
                int ringtoneID = cursor.getInt(cursor
                        .getColumnIndex(MediaStore.MediaColumns._ID));
                mImageUri = Uri.withAppendedPath(mUri, ""
                        + ringtoneID);
               
            }
            cursor.moveToNext();
        }
		return mImageUri;
	}
	 /**
	  * 将Drawable转化为Bitmap
	  * @param drawable
	  * @return
	  */
	 public static Bitmap drawable2Bitmap(Drawable drawable) {
	        // 取 drawable 的长宽
	        int w = drawable.getIntrinsicWidth();
	        int h = drawable.getIntrinsicHeight();

	        // 取 drawable 的颜色格式
	        Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_8888
	                : Config.RGB_565;
	        // 建立对应 bitmap
	        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
	        // 建立对应 bitmap 的画布
	        Canvas canvas = new Canvas(bitmap);
	        drawable.setBounds(0, 0, w, h);
	        // 把 drawable 内容画到画布中
	        drawable.draw(canvas);
	        return bitmap;
	    }
	 /**  
	 *  最省内存的方式读取本地资源的图片  很少出现OOM
	 * @param
	 * @param resId 
	 * @return 
	 */ 
	 public static Bitmap Resources2Bitmap(Resources resources, int resId){ 
	 BitmapFactory.Options opt = new BitmapFactory.Options(); 
	 opt.inPreferredConfig = Config.RGB_565;
	 opt.inPurgeable = true; 
	 opt.inInputShareable = true; 
	 //获取资源图片 
	 InputStream is = resources.openRawResource(resId); 
	 return BitmapFactory.decodeStream(is,null,opt); 
	 }
	 /**
	  * Bitmap2Drawable
	  * BtimapDrawable是Drawable的子类
	  * @param resources
	  * @param bitmap
	  * @return
	  */
	 public static BitmapDrawable Bitmap2Drawable(Resources resources,Bitmap bitmap) {
		// TODO 自动生成的方法存根
		/*	//Bitmap转换成Drawable  因为BtimapDrawable是Drawable的子类，最终直接使用bd对象即可。
		 Bitmap bm=xxx; //xxx根据你的情况获取
		 BitmapDrawable bd= new BitmapDrawable(getResource(), bm); */
		 BitmapDrawable bitmapDrawable= new BitmapDrawable(resources, bitmap);
		 return bitmapDrawable;
	}
	 /**
	  * 获得圆角图片
	  * @param bitmap
	  * @param roundPx 要切的像素大小 
	  * @return
	  */
	 public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {
	        int w = bitmap.getWidth();
	        int h = bitmap.getHeight();
	        Bitmap output = Bitmap.createBitmap(w, h, Config.ARGB_8888);
	        Canvas canvas = new Canvas(output);
	        final int color = 0xff424242;
	        final Paint paint = new Paint();
	        final Rect rect = new Rect(0, 0, w, h);
	        final RectF rectF = new RectF(rect);
	        paint.setAntiAlias(true);
	        canvas.drawARGB(0, 0, 0, 0);
	        paint.setColor(color);
	        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
	        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
	        canvas.drawBitmap(bitmap, rect, rect, paint);

	        return output;
	    }
	 /**
	  * 获得带倒影的图片
	  * @param bitmap
	  * @return
	  */
	 public static Bitmap createReflectionImageWithOrigin(Bitmap bitmap) {
	        final int reflectionGap = 4;
	        int w = bitmap.getWidth();
	        int h = bitmap.getHeight();

	        Matrix matrix = new Matrix();
	        matrix.preScale(1, -1);

	        Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, h / 2, w,
	                h / 2, matrix, false);

	        Bitmap bitmapWithReflection = Bitmap.createBitmap(w, (h + h / 2),
	                Config.ARGB_8888);

	        Canvas canvas = new Canvas(bitmapWithReflection);
	        canvas.drawBitmap(bitmap, 0, 0, null);
	        Paint deafalutPaint = new Paint();
	        canvas.drawRect(0, h, w, h + reflectionGap, deafalutPaint);

	        canvas.drawBitmap(reflectionImage, 0, h + reflectionGap, null);

	        Paint paint = new Paint();
	        LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0,
	                bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff,
	                0x00ffffff, TileMode.CLAMP);
	        paint.setShader(shader);
	        // Set the Transfer mode to be porter duff and destination in
	        paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
	        // Draw a rectangle using the paint with our linear gradient
	        canvas.drawRect(0, h, w, bitmapWithReflection.getHeight()
	                + reflectionGap, paint);

	        return bitmapWithReflection;
	    }
	 /**
	  * Drawable缩放  没有做OOM溢出处理
	  * @param drawable
	  * @param w
	  * @param h
	  * @return
	  */
	 public static Drawable zoomDrawableWithWidthHeight(Drawable drawable, int w, int h) {
	        int width = drawable.getIntrinsicWidth();
	        int height = drawable.getIntrinsicHeight();
	        // drawable转换成bitmap
	        Bitmap oldbmp = drawable2Bitmap(drawable);
	        // 创建操作图片用的Matrix对象
	        Matrix matrix = new Matrix();
	        // 计算缩放比例
	        float sx = ((float) w / width);
	        float sy = ((float) h / height);
	        // 设置缩放比例
	        matrix.postScale(sx, sy);
	        // 建立新的bitmap，其内容是对原bitmap的缩放后的图
	        Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height,
	                matrix, true);
	        return new BitmapDrawable(newbmp);
	    }

		
		/**
		 * 图片压缩方法实现   OOM溢出处理了
		 * @param srcPath 原图地址
		 * @param finishPath 压缩后保存图片地址
		 * @param avatorpath 保存的文件夹路径
		 * @return
		 */
		public static Bitmap zoomBitmapByPathAndSaveNewImg(String srcPath,String finishPath,String avatorpath) {
			BitmapFactory.Options newOpts = new BitmapFactory.Options();
			// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
			newOpts.inJustDecodeBounds = true;
			Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
			newOpts.inJustDecodeBounds = false;
			int w = newOpts.outWidth;
			int h = newOpts.outHeight;
			int hh = 800;// 这里设置高度为800f
			int ww = 480;// 这里设置宽度为480f
			// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
			int be = 1;// be=1表示不缩放
			if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
				be = (int) (newOpts.outWidth / ww);
			} else if (w < h && h > hh) {// 如果高度高的话根据高度固定大小缩放
				be = (int) (newOpts.outHeight / hh);
			}
			if (be <= 0)
				be = 1;
			newOpts.inSampleSize = be;// 设置缩放比例
			// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
			bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
			return compressImage(bitmap,finishPath,avatorpath);// 压缩好比例大小后再进行质量压缩
		}	
		

		/**
		 * 质量压缩
		 * @param image
		 * @return
		 */
		private static Bitmap compressImage(Bitmap image,String finishPath,String avatorpath) {

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			image.compress(CompressFormat.JPEG, 60, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
			int options = 60;
			while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
				baos.reset();// 重置baos即清空baos
				options -= 10;// 每次都减少10
				image.compress(CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中

			}
			ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
			Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
			try {
				File fileDir = new File(avatorpath);
				if (!fileDir.exists()) {
					fileDir.mkdirs();// 创建文件夹
				}
				FileOutputStream out = new FileOutputStream(finishPath);
				bitmap.compress(CompressFormat.PNG, 60, out);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return bitmap;
		}
		
	
	
		/**
		 * 保存图片
		 * @param bitmap
		 * @param picName
		 * @param savePath  "/sdcard/namecard/"
		 * @return
		 */
				
		public static String saveBitmap(Bitmap  bitmap,String picName,String savePath) { 
			// 判断存储卡是否可以用，可用进行存储
			String environmentState = Environment.getExternalStorageState();
			if (environmentState.equals(Environment.MEDIA_MOUNTED)) {
				File fileDir= new File(savePath);
				if (!fileDir.exists()) {
					fileDir.mkdirs();// 创建文件夹cameraPhotoPath
				}
				//Log.e(TAG, "保存图片"); 
				File f = new File(savePath, picName); 
				if (f.exists()) { 
				f.delete(); 
				} 
		try { 
		FileOutputStream out = new FileOutputStream(f); 
		bitmap.compress(CompressFormat.PNG, 100, out);
		out.flush(); 
		out.close(); 
		//Log.i(TAG, "已经保存"); 
		return savePath+picName;
		
		} catch (FileNotFoundException e) { 
		// TODO Auto-generated catch block 
		e.printStackTrace(); 
		} catch (IOException e) { 
		// TODO Auto-generated catch block 
		e.printStackTrace(); 
		} 
		}else{
			
			System.err.println("ImageUtil  sd卡不可用");	
			return "ImageUtil sd卡不可用";
		}
			return "保存出错";
		}
		
		/**
		 * 保存图片到图库
		 * 2015年4月22日14:40:14  louisgeek
		 * @param activity
		 * @param bmp
		 */
		public  void saveImageToGallery(Activity activity, Bitmap bmp) {	
		
			//A02保存图片到图库
			    String uriGalleryStr= MediaStore.Images.Media.insertImage(activity.getContentResolver(), bmp, "", "");
			   String pathGalleryStr= getImagePathByUri(Uri.parse(uriGalleryStr),activity);
			   
			 //A02最后通知图库内指定文件更新  需要的是  file://  的uri
			   activity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,Uri.parse("file://"+pathGalleryStr))); 
			    
			    // 通知指定目录更新  无效
			   // sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_DIR", Uri.parse("file://"+ Environment.getExternalStorageDirectory())));
			   
			    //通知指定SD卡更新 4.4以上不支持  下面注释的另一个方法
			  //  sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://"+ Environment.getExternalStorageDirectory()))); 
			    //ToastUtil.show(activity, "保存成功，可到本地相册进行查看!",Toast.LENGTH_SHORT);
			} 


		//第一种方法 需要新线程   4.1估计有错误  
	    public static Bitmap getBitmapFromUrlByHttpURLConnection(String strUrl)  
	    {  
	        Bitmap bitmap = null;  
	        try  
	        {  
	            //初始化一个URL对象  
	            URL url = new URL(strUrl);  
	            //获得HTTPConnection网络连接对象  
	            HttpURLConnection connection = (HttpURLConnection) url.openConnection();  
	            connection.setConnectTimeout(5*1000);  
	            connection.setDoInput(true);  
	            connection.connect();  
	            //得到输入流  
	            InputStream is = connection.getInputStream();  
	            Log.i("TAG", "*********inputstream**"+is);  
	            bitmap = BitmapFactory.decodeStream(is);  
	            Log.i("TAG", "*********bitmap****"+bitmap);  
	            //关闭输入流  
	            is.close();  
	            //关闭连接  
	            connection.disconnect();  
	        } catch (Exception e)  
	        {  
	            // TODO Auto-generated catch block  
	            e.printStackTrace();  
	        }  
	          
	        return bitmap;  
	    }  
	    

	  //把bitmap转换成base64
	 	public static String getBase64PicStrFromBitmap(Bitmap bitmap, int bitmapQuality)
	 	{
	 		ByteArrayOutputStream bStream = new ByteArrayOutputStream();
	 		bitmap.compress(CompressFormat.PNG, bitmapQuality, bStream);
	 		byte[] bytes = bStream.toByteArray();
	 		return Base64.encodeToString(bytes, Base64.DEFAULT);
	 	}
	 	//把base64转换成bitmap
	 	public static Bitmap getBitmapFromBase64PicStr(String PicString)
	 	{
	 		byte[] bitmapArray = null;
	 		try {
	 		bitmapArray = Base64.decode(PicString, Base64.DEFAULT);
	 		} catch (Exception e) {
	 		e.printStackTrace();
	 		}
	 		return BitmapFactory.decodeByteArray(bitmapArray, 0,bitmapArray.length);
	 	}

	  public static String getBase64ImgsJsonStr(String filePath, String fileTime, int nowUserID) {

		File file=new File(filePath);
		String fileName=file.getName();
		/*Bitmap bitmap=null;
		if (filePath !=null && filePath.length() > 0) {
			bitmap = ImageUtil.zoomBitmapFromFileWithWidthHeight(file, 400, 400);
		}
		  bitmap=imageBitmap;
		byte[] imgBytes = ImageUtil.Bitmap2Bytes(bitmap);
		String Base64Str= Base64.encodeToString(imgBytes, Base64.DEFAULT);*/
		  String Base64Str=PictureUtil.bitmapToString(filePath);

		List<UploadBase64ImgsBean> list=new ArrayList<>();

		String prefix=fileName.substring(fileName.lastIndexOf(".")+1);
		System.out.println(prefix);
		//用String的endsWith(".java")方法判断是否问指定文件类型。
		String prefixHasDot="."+prefix;
		UploadBase64ImgsBean imgs1=new UploadBase64ImgsBean(nowUserID, fileName,fileTime, prefixHasDot, Base64Str);
		// UploadImgs imgs2=new UploadImgs(NowUserID, "120.jpg", ".jpg", Base64Str);
		list.add(imgs1);
		// list.add(imgs2);
		Gson gson=new Gson();
		String base64ImgsJsonStr= gson.toJson(list);
		return base64ImgsJsonStr;

	}

	public static String getBase64WithWeatherImgsJsonStr(String filePath,String fileTime,UploadBase64ImgsWithWeatherBean uploadBase64ImgsWithWeatherBean) {
		UploadBase64ImgsWithWeatherBean mUploadBase64ImgsWithWeatherBean=uploadBase64ImgsWithWeatherBean;

		File file=new File(filePath);
		String fileName=file.getName();
		/*
		Bitmap bitmap=null;
		if (filePath !=null && filePath.length() > 0) {
			bitmap = ImageUtil.zoomBitmapFromFileWithWidthHeight(file, 400, 400);
		}
		bitmap=imageBitmap;
		byte[] imgBytes = ImageUtil.Bitmap2Bytes(bitmap);
		String Base64Str= Base64.encodeToString(imgBytes, Base64.DEFAULT);*/

		String Base64Str=PictureUtil.bitmapToString(filePath);

		//List<UploadBase64ImgsBean> list=new ArrayList<>();
		List<UploadBase64ImgsWithWeatherBean.PlantimgsstrBean> plantimgsstr=new ArrayList<>();
		String prefix=fileName.substring(fileName.lastIndexOf(".")+1);
		System.out.println(prefix);
		//用String的endsWith(".java")方法判断是否问指定文件类型。
		String prefixHasDot="."+prefix;
		/*UploadBase64ImgsBean imgs1=new UploadBase64ImgsBean(mUploadBase64ImgsWithWeatherBean.getUserid(),
				fileName,fileTime, prefixHasDot, Base64Str);*/
		UploadBase64ImgsWithWeatherBean.PlantimgsstrBean plantimgsstrBean1=new UploadBase64ImgsWithWeatherBean.PlantimgsstrBean();
		plantimgsstrBean1.setPlantImgStr(Base64Str);
		plantimgsstrBean1.setPlantoldfilename(fileName);
		plantimgsstrBean1.setPlantextension(prefixHasDot);
		plantimgsstrBean1.setShoottime(fileTime);
		// UploadImgs imgs2=new UploadImgs(NowUserID, "120.jpg", ".jpg", Base64Str);
		//list.add(imgs1);
		// list.add(imgs2);
		plantimgsstr.add(plantimgsstrBean1);
		Gson gson=new Gson();
		//String base64ImgsJsonStr= gson.toJson(list);

		mUploadBase64ImgsWithWeatherBean.setPlantimgsstr(plantimgsstr);//set
		String plantimgsstrBase64ImgsJsonStr= gson.toJson(mUploadBase64ImgsWithWeatherBean);
		return  plantimgsstrBase64ImgsJsonStr;
	}
}
