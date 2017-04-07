package com.thoughtworks.videorental.main;

import com.thoughtworks.videorental.action.LoginAction;
import com.thoughtworks.videorental.action.LogoutAction;
import com.thoughtworks.videorental.action.ViewCurrentRentalsAction;
import com.thoughtworks.videorental.action.ViewHistoryAction;
import com.thoughtworks.videorental.action.ViewHomeAction;
import com.thoughtworks.videorental.domain.repository.CustomerRepository;
import com.thoughtworks.videorental.domain.repository.MovieRepository;
import com.thoughtworks.videorental.domain.repository.TransactionRepository;
import com.thoughtworks.videorental.toolkit.web.Router;
import com.thoughtworks.videorental.toolkit.web.WebRequest;
import com.thoughtworks.videorental.toolkit.web.WebResponse;

public class VideoWorldRouter {
	private Router router = new Router();

	public VideoWorldRouter(CustomerRepository customerRepository, MovieRepository movieRepository, TransactionRepository transactionRepository) {
		router.addUnprotectedRoute("/login", 	new LoginAction(customerRepository));
		router.addRoute("/", 		new ViewHomeAction(movieRepository));
		router.addRoute("/rentals",	new ViewCurrentRentalsAction(transactionRepository));
        router.addRoute("/logout", 	new LogoutAction());
        router.addRoute("/history", new ViewHistoryAction(transactionRepository));
	}

	public void service(WebRequest request, WebResponse response) {
		router.service(request, response);
	}
}
