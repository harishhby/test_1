package com.ta3s.helper.pagehelper;

import java.io.IOException;

import com.ta3s.helper.objhelper.WebPage;

public class Ta3sLoginPage extends WebPage {

	public void clickOnLoginLink() throws IOException
	{
		click("signInLink");
	}
	
	
}
