package com.inventory.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

import javax.imageio.ImageIO;

public class FileUtil {

	public static String writeImageToLocal(String base64,String name) {
		if(base64==null) {
			return "N/A";
		}
		try {
			String ext=getImageExt(base64);
			byte[] decoded=Base64.getDecoder().decode(base64);
			BufferedImage bufferedImage=ImageIO.read(new ByteArrayInputStream(decoded));
			ImageIO.write(bufferedImage, ext, new File("images/"+name+"."+ext));
			return "images/"+name+"."+ext;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "null";
		
	}
	
	
	public static String getImageExt(String data) {
		if(data.charAt(0)=='/')
			return "jpeg";
		else if(data.charAt(0)=='i')
			return "png";
		return "png";
	}
}
