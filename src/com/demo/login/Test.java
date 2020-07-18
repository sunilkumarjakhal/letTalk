package com.demo.login;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

@Controller
public class Test {
	
	@RequestMapping(value = "/test1", method = RequestMethod.GET)
	public ModelAndView register(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView mav = new ModelAndView("test");
		mav.addObject("register", new RegistrationDataPojo1());
		return mav;

	}
	
	@RequestMapping(value = "/test2", method = RequestMethod.GET)
	public ModelAndView register2(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView mav = new ModelAndView("test2");
		mav.addObject("register", new RegistrationDataPojo1());
		return mav;

	}

	@ResponseBody
	@RequestMapping(value = "/registerProcess1", method = RequestMethod.POST)
	public ModelAndView registerProcess(HttpServletRequest request, HttpServletResponse response,
			@Validated(RegistrationDataPojo1.ValidationStepOne.class) @ModelAttribute("register") RegistrationDataPojo registrationData, BindingResult br) {		
		ModelAndView mav = null;

			if (br.hasErrors()) {
				mav = new ModelAndView("test");
				mav.addObject("message", "Something goes wrong !!!");
				return mav;
			}
			else {
				
			Gson gson = new Gson();
			System.out.println(gson.toJson(registrationData));
				
			}
			return mav;

			}

	@ResponseBody
	@RequestMapping(value = "/registerProcess2", method = RequestMethod.POST)
	public ModelAndView registerProcess2(HttpServletRequest request, HttpServletResponse response,
			@Validated(RegistrationDataPojo1.ValidationStepTwo.class)  @ModelAttribute("register") RegistrationDataPojo registrationData, BindingResult br) {

		ModelAndView mav = null;

		if (br.hasErrors()) {
			mav = new ModelAndView("test2");
			mav.addObject("message", "Something goes wrong !!!");
			return mav;
		}
		else {
			Gson gson = new Gson();
			System.out.println(gson.toJson(registrationData));
		}
		return mav;

		

	}


}

//https://stackoverflow.com/questions/29798346/exclude-some-fields-from-valid-validation..
//https://www.javacodegeeks.com/2014/08/validation-groups-in-spring-mvc.html

class RegistrationDataPojo1 {
	
	 interface ValidationStepOne {
	        // validation group marker interface
	    }
	 
	    interface ValidationStepTwo {
	        // validation group marker interface
	    }

	@NotNull(groups = {ValidationStepOne.class})
	@Size(min=4, max=50 , message = "username length should be grater than 3 character",groups = {ValidationStepOne.class})
	private String username;
	
	@NotNull
	@Size(min=4, max=50 , message = "password length should be grater than 3 character")
	private String password;
	
	@NotNull
	@Size(min=4, max=50 , message = "confirm password length should be grater than 3 character")
	private String cpassword;
	
	@NotNull(groups = {ValidationStepTwo.class})
	@Size(groups = {ValidationStepTwo.class},min=4, max=50 , message = "first name length should be grater than 3 character")
	private String firstName;
	
	@NotNull
	@Size(min=4, max=50 , message = "last name length should be grater than 3 character")
	private String lastName;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCpassword() {
		return cpassword;
	}

	public void setCpassword(String cpassword) {
		this.cpassword = cpassword;
	}

}
