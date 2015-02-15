package com.example.testvaadin.util;

import java.io.File;

import com.vaadin.server.FileResource;
import com.vaadin.server.Resource;
import com.vaadin.server.VaadinService;

public class ResourceUtil {

	private final static String basepath = VaadinService.getCurrent()
			.getBaseDirectory().getAbsolutePath();
	private final static Resource plusImg = new FileResource(new File(basepath
			+ "/WEB-INF/images/button_images/plus.png"));
	private final static Resource minusImg = new FileResource(new File(basepath
			+ "/WEB-INF/images/button_images/minus.png"));
	private final static Resource pingImg = new FileResource(new File(basepath
			+ "/WEB-INF/images/button_images/ping.png"));
	private final static Resource cleanImg = new FileResource(new File(basepath
			+ "/WEB-INF/images/button_images/clean.png"));
	private final static Resource startImg = new FileResource(new File(basepath
			+ "/WEB-INF/images/button_images/start.png"));
	private final static Resource settingsImg = new FileResource(new File(
			basepath + "/WEB-INF/images/button_images/settings.png"));
	private final static Resource ev97Img = new FileResource(new File(basepath
			+ "/WEB-INF/images/ev97.jpg"));
	private final static Resource boeing737Img = new FileResource(new File(
			basepath + "/WEB-INF/images/boeing737.jpg"));

	public static Resource getPlusImgResource() {
		return plusImg;
	}

	public static Resource getMinusImgResource() {
		return minusImg;
	}

	public static Resource getPingImg() {
		return pingImg;
	}

	public static Resource getCleanImg() {
		return cleanImg;
	}

	public static Resource getStartImg() {
		return startImg;
	}

	public static Resource getSettingsImg() {
		return settingsImg;
	}

	public static Resource getEv97Img() {
		return ev97Img;
	}

	public static Resource getBoeing737Img() {
		return boeing737Img;
	}

}
