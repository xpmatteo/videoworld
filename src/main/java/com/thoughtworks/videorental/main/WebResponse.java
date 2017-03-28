package com.thoughtworks.videorental.main;

public interface WebResponse {

	void redirectTo(String location);

	void render(String templateName, String layoutName);

	void setStatus(int statusCode);

	void renderText(String text);

}
