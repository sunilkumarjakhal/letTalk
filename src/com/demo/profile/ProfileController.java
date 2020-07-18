package com.demo.profile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.demo.util.ConstantConfig;
import com.demo.util.MongoDBConnection;
import com.demo.util.Util;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

@Controller
public class ProfileController {

	Util util = new Util();

	@RequestMapping(value = "/editProfile", method = RequestMethod.GET)
	public ModelAndView getProfile(HttpServletRequest request, HttpServletResponse response) {

		try {

			HttpSession session = request.getSession();
			String name = (String) session.getAttribute("letsTalk_username");

			if (name != null && name.length() > 0) {

				// Map referenceData = new HashMap();
				// referenceData.put("countryList", util.getCountryList());

				Gson gson = new Gson();
				System.out.println(util.getCountryList());
				request.setAttribute("countryList", util.getCountryList());
				ModelAndView mav = new ModelAndView("edit_profile");
				mav.addObject("firstname", name);
				mav.addObject("profileDataPojo", new ProfileDataPojo());
				mav.addObject("data", getMyProfileData(name));

				return mav;

			} else {
				ModelAndView mav = new ModelAndView("login");
				mav.addObject("message", "You are logged out!!");
				return mav;
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;
	}

	@RequestMapping(value = "/editProfileProcess", method = RequestMethod.POST)
	public ModelAndView editProfile(HttpServletRequest request, HttpServletResponse response,
			@Valid @ModelAttribute ProfileDataPojo pojo) throws IOException {

		Gson gson = new Gson();
		System.out.println(gson.toJson(pojo));

		ModelAndView mav = new ModelAndView("profile");

		HttpSession session = request.getSession();
		String name = (String) session.getAttribute("letsTalk_username");

		if (name != null && name.length() > 0) {
			mav = new ModelAndView("profile");
			if (MongoDBConnection.getDB() == null || MongoDBConnection.getMongoClient() == null) {

				if (MongoDBConnection.makeDBConnection(ConstantConfig.DB_IP, ConstantConfig.DB_PORT)) {
					// System.out..println("DB is again connected");
				} else {

					// System.out..println("DB is not connecting.........");
				}
			}

			DBCollection table = MongoDBConnection.getDB().getCollection("tbl_users_detail_data");

			BasicDBObject query = new BasicDBObject();
			query.put("username", name);

			BasicDBObject document = new BasicDBObject();
			document.put("country", pojo.getCountry());
			document.put("state", pojo.getState());
			document.put("city", pojo.getCity());
			document.put("address", pojo.getAddress());
			document.put("mobile_no", pojo.getMobileNo());
			document.put("about_you", pojo.getAboutYou());
			document.put("hobby", pojo.getHobby());

			document.put("email_id", pojo.getEmailId());
			document.put("gender", pojo.getGender());
			document.put("dob", pojo.getDob());

			MultipartFile multipartFile = pojo.getFile();
			System.out.println(multipartFile);

			String filename = name + "." + multipartFile.getOriginalFilename()
					.substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1);
			System.out.println(filename);
			// String rootPath = System.getProperty("catalina.home");
			// System.out.println(rootPath);
			// String uploadPath = context.getRealPath("") + File.separator +
			// "temp" + File.separator;
			// File uploadPath=new
			// File(request.getContextPath()+"/WebContent/WEB-INF/FileStorage/");

			String uploadPath = "H:\\sunil fr\\Workspace\\LetsTalk\\WebContent\\resources\\FileStorage\\";

			// Now do something with file...
			// FileCopyUtils.copy(file.getFile().getBytes(), new
			// File(uploadPath+file.getFile().getOriginalFilename()));
			FileCopyUtils.copy(pojo.getFile().getBytes(), new File(uploadPath + filename));

			document.put("image_url", uploadPath + filename);

			table.update(query, new BasicDBObject("$set", document), true, true);

			mav = new ModelAndView("profile");
			mav.addObject("data", getMyProfileData(name));
			return mav;
		}

		else {
			mav = new ModelAndView("login");
			mav.addObject("message", "You are logged out!!");
			return mav;

		}

	}

	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public ModelAndView getProfileData(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView mav = new ModelAndView("profile");

		HttpSession session = request.getSession();
		String name = (String) session.getAttribute("letsTalk_username");
		ProfileController profileController = new ProfileController();
		if (name != null && name.length() > 0) {
			mav = new ModelAndView("profile");

			mav.addObject("data", profileController.getMyProfileData(name));
			return mav;
		}

		else {
			mav = new ModelAndView("login");
			mav.addObject("message", "You are logged out!!");
			return mav;

		}

	}

	@RequestMapping(value = "/friendProfile", method = RequestMethod.GET)
	public ModelAndView getFriendProfileData(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String queryName) {

		ModelAndView mav = new ModelAndView("profile");

		HttpSession session = request.getSession();
		String name = (String) session.getAttribute("letsTalk_username");
		ProfileController profileController = new ProfileController();
		if (name != null && name.length() > 0) {
			mav = new ModelAndView("friend_profile");

			boolean status = isMyFriend(name, queryName);

			// mav.addObject("friend_name", queryName);
			mav.addObject("isMyFriend", status);
			mav.addObject("data", profileController.getMyProfileData(queryName));

			boolean status2 = profileController.isBlocked(name, queryName);
			mav.addObject("isBlocked", status2);

			return mav;
		}

		else {
			mav = new ModelAndView("login");
			mav.addObject("message", "You are logged out!!");
			return mav;

		}

	}

	public boolean isMyFriend(String name, String queryName) {

		try {

			List<String> friendsList = new ArrayList<String>();

			Util util = new Util();
			BasicDBList dbList = util.findFriendList(name);

			if (dbList != null && dbList.size() > 0) {
				for (Object f : dbList) {
					friendsList.add(f.toString());
				}

			}
			if (friendsList != null && friendsList.size() > 0 && friendsList.contains(queryName)) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	public boolean isBlocked(String name, String queryName) {

		try {

			if (MongoDBConnection.getDB() == null || MongoDBConnection.getMongoClient() == null) {

				if (MongoDBConnection.makeDBConnection(ConstantConfig.DB_IP, ConstantConfig.DB_PORT)) {
					// System.out..println("DB is again connected");
				} else {

					// System.out..println("DB is not connecting.........");
				}
			}

			DBCollection table2 = MongoDBConnection.getDB().getCollection("tbl_block_list_data");

			BasicDBObject query1 = new BasicDBObject();
			query1.put("username", name);
			query1.put("block_list.name", queryName);

			DBCursor friends = table2.find(query1);

			if (friends.size() > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	public ProfileDataPojo getMyProfileData(String userName) {

		ProfileDataPojo profileDataPojo = new ProfileDataPojo();
		try {
			// System.out.println(userName);
			if (MongoDBConnection.getDB() == null || MongoDBConnection.getMongoClient() == null) {

				if (MongoDBConnection.makeDBConnection(ConstantConfig.DB_IP, ConstantConfig.DB_PORT)) {
					// System.out..println("DB is again connected");
				} else {

					// System.out..println("DB is not connecting.........");
				}
			}

			DBCollection table = MongoDBConnection.getDB().getCollection("tbl_users_detail_data");

			BasicDBObject query = new BasicDBObject();
			query.put("username", userName);
			System.out.println(query);
			DBCursor cursor = table.find(query);
			BasicDBObject oneDetail = null;

			System.out.println(cursor.size());

			while (cursor.hasNext()) {
				oneDetail = (BasicDBObject) cursor.next();

				if (oneDetail.containsField("username")) {
					profileDataPojo.setUsername(oneDetail.getString("username"));
				}
				if (oneDetail.containsField("first_name")) {
					profileDataPojo.setFirstName(oneDetail.getString("first_name"));
				}
				if (oneDetail.containsField("last_name")) {
					profileDataPojo.setLastName(oneDetail.getString("last_name"));
				}
				if (oneDetail.containsField("email_id")) {
					profileDataPojo.setEmailId(oneDetail.getString("email_id"));
				}
				if (oneDetail.containsField("country")) {
					profileDataPojo.setCountry(oneDetail.getString("country"));
				}
				if (oneDetail.containsField("city")) {
					profileDataPojo.setCity(oneDetail.getString("city"));
				}
				if (oneDetail.containsField("state")) {
					profileDataPojo.setState(oneDetail.getString("state"));
				}
				if (oneDetail.containsField("address")) {
					profileDataPojo.setAddress(oneDetail.getString("address"));
				}
				if (oneDetail.containsField("dob")) {
					profileDataPojo.setDob(oneDetail.getString("dob"));
				}
				if (oneDetail.containsField("gender")) {
					profileDataPojo.setGender(oneDetail.getString("gender"));
				}
				if (oneDetail.containsField("mobile_no")) {
					profileDataPojo.setMobileNo(oneDetail.getString("mobile_no"));
				}
				if (oneDetail.containsField("image_url")) {
					profileDataPojo.setImageUrl(oneDetail.getString("image_url"));
				}
				if (oneDetail.containsField("about_you")) {
					profileDataPojo.setAboutYou(oneDetail.getString("about_you"));
				}
				List<String> hobbyList = new ArrayList<String>();

				if (oneDetail.containsField("hobby")) {

					BasicDBList list = (BasicDBList) oneDetail.get("hobby");
					if (list.size() > 0) {
						for (Object l : list) {
							hobbyList.add(l.toString());
						}
						profileDataPojo.setHobby(hobbyList);
					}
					if (hobbyList.size() > 0) {
						String hobbyString = hobbyList.stream().collect(Collectors.joining(","));
						profileDataPojo.setHobbyString(hobbyString);
					}
				}
			}

			Gson gson = new Gson();
			System.out.println("sendt data : " + gson.toJson(profileDataPojo));
			return profileDataPojo;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return profileDataPojo;
	}

	@RequestMapping(value = "/getStateFromCountry", method = RequestMethod.GET)
	public void getStateData(@RequestParam String country, HttpServletRequest req, HttpServletResponse res)
			throws Exception {

		System.out.println("country : " + country);
		Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();

		if (country.equalsIgnoreCase("NONE")) {

			Map<String, String> map = new HashMap<>();
			map.put("NONE", "--- Select ---");
			String mapData = prettyGson.toJson(map);

			res.getWriter().write(mapData);

		}

		else {

			Map<String, String> map = util.getStateList(country);

			if (map != null && map.size() > 0) {

				String mapData = prettyGson.toJson(map);
				res.getWriter().write(mapData);
			} else {
				map = new HashMap<>();
				map.put("NONE", "--- Select ---");
				map.put("Other", "Other");
				String mapData = prettyGson.toJson(map);
				res.getWriter().write(mapData);

			}

		}
	}

	@RequestMapping(value = "/getCityFromState", method = RequestMethod.GET)
	public void getCityData(@RequestParam String state, HttpServletRequest req, HttpServletResponse res)
			throws Exception {

		System.out.println("state : " + state);
		Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();

		if (state.equalsIgnoreCase("NONE")) {

			Map<String, String> map = new HashMap<>();
			map.put("NONE", "--- Select ---");
			String mapData = prettyGson.toJson(map);

			res.getWriter().write(mapData);

		}

		else {

			Map<String, String> map = util.getCityList(state);

			if (map != null && map.size() > 0) {

				String mapData = prettyGson.toJson(map);
				res.getWriter().write(mapData);
			} else {
				map = new HashMap<>();
				map.put("NONE", "--- Select ---");
				map.put("Other", "Other");
				String mapData = prettyGson.toJson(map);
				res.getWriter().write(mapData);

			}

		}
	}
}
