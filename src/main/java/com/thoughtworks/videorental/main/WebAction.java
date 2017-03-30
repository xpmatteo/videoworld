package com.thoughtworks.videorental.main;

import com.thoughtworks.videorental.toolkit.WebRequest;
import com.thoughtworks.videorental.toolkit.WebResponse;

public interface WebAction {
	void accept(WebRequest request, WebResponse response);
}
