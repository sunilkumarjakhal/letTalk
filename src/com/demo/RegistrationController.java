//package com.demo;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.validation.Valid;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.servlet.ModelAndView;
//
//import com.demo.login.UserDetailsPojo;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//
//@Controller
//public class RegistrationController {
//
//	@RequestMapping(value = "/editProfile", method = RequestMethod.GET)
//	public ModelAndView showRegister(HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//		RegistrationController a = new RegistrationController();
//		request.setAttribute("countryList", a.referenceData());
//		ModelAndView mav = new ModelAndView("edit_profile");
//	//	mav.addObject("user", new UserDetailsPojo());
//		return mav;
//
//	}
//
//	@RequestMapping(value = "/getCitiesForState", method = RequestMethod.GET)
//	public void getStateData(@RequestParam String state,HttpServletRequest req , HttpServletResponse res) throws Exception {
//
//		System.out.println("data : " + state);
//		List<State> stateList = new ArrayList<State>();
//		
//		if(state.equalsIgnoreCase("NONE")){
//			
//			Map<String,String> map = new HashMap<>();
//			map.put("NONE", "--- Select ---");
//
//			
//			Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
//			String mapData = prettyGson.toJson(map);
//
//			res.getWriter().write(mapData);
//
//		}
//		
//		else if (state.equalsIgnoreCase("India")) {
//			
//			Map<String,String> map = new HashMap<>();
//			map.put("Haryana1", "Haryana1");
//			map.put("Haryana2", "Haryana2");
//			map.put("Haryana3", "Haryana3");
//			map.put("Haryana4", "Haryana4");
//			map.put("Haryana5", "Haryana5");
//			
//			Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
//			String mapData = prettyGson.toJson(map);
//
//		//	System.out.println(mapData);
//			
//			res.getWriter().write(mapData);
//			
//
//		} else {
//			Map<String,String> map = new HashMap<>();
//			map.put("AA", "AA");
//			map.put("BB", "BB");
//			map.put("CC", "CC");
//			map.put("DD", "DD");
//		
//
//			Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
//			String mapData = prettyGson.toJson(map);
//
//			//System.out.println(mapData);
//			
//			res.getWriter().write(mapData);
//
//		}
//		
//
//	}
//
//	@RequestMapping(value = "/editProfileProcess", method = RequestMethod.POST)
//	public ModelAndView addUser(HttpServletRequest request, HttpServletResponse response,
//			@Valid @ModelAttribute("user") UserDetailsPojo user, BindingResult br) throws Exception {
//		if (br.hasErrors()) {
//			RegistrationController a = new RegistrationController();
//			request.setAttribute("countryList", a.referenceData());
//			return new ModelAndView("register");
//		}
//
////		System.out.println("Username : " + user.getUsername());
////		System.out.println("Password : " + user.getPassword());
//		System.out.println("Firstname : " + user.getFirstname());
//		System.out.println("Lastname : " + user.getLastname());
//		System.out.println("Email : " + user.getEmail());
//		System.out.println("Address : " + user.getAddress());
//		System.out.println("Phone : " + user.getPhone());
//		System.out.println("Country : " + user.getCountry());
//		System.out.println("Country : " + user.getState());
//		System.out.println("DOB : " + user.getDob());
//		System.out.println("Gender : " + user.getGender());
//		for (int i = 0; i < user.getHobby().length; i++) {
//			System.out.println("Hobby : " + user.getHobby()[i]);
//		}
//
//		if (user.getFirstname() != null) {
//			return new ModelAndView("welcome", "firstname", user.getFirstname());
//
//		}
//		return null;
//
//	}
//
//	protected Map<String, String> referenceData() throws Exception {
//		// Map referenceData = new HashMap();
//		Map<String, String> country = new LinkedHashMap<String, String>();
//		country.put("India", "India");
//		country.put("China", "China");
//		country.put("Singapore", "Singapore");
//		country.put("Malaysia", "Malaysia");
//		country.put("United Stated", "United Stated");
//		// referenceData.put("countryList", country);
//		return country;
//	}
//
//}
//
//class State {
//	String id, state;
//
//	public State(String id, String state) {
//		super();
//		this.id = id;
//		this.state = state;
//	}
//
//	public String getId() {
//		return id;
//	}
//
//	public void setId(String id) {
//		this.id = id;
//	}
//
//	public String getState() {
//		return state;
//	}
//
//	public void setState(String state) {
//		this.state = state;
//	}
//}