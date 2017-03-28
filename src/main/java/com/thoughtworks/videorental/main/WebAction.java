package com.thoughtworks.videorental.main;

import java.util.function.BiConsumer;

import com.thoughtworks.videorental.toolkit.WebRequest;
import com.thoughtworks.videorental.toolkit.WebResponse;

public interface WebAction extends BiConsumer<WebRequest, WebResponse> {

}
