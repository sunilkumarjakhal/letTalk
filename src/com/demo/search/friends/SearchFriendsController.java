package com.demo.search.friends;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.demo.util.ConstantConfig;
import com.demo.util.MongoDBConnection;
import com.demo.util.Util;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

@Controller
public class SearchFriendsController {

	@RequestMapping(value = "search", method = RequestMethod.GET)
	public ModelAndView searchFriend(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String queryName) throws IOException {

		ModelAndView mav = new ModelAndView("search_friend");
		try {
			HttpSession session = request.getSession();
			String name = (String) session.getAttribute("letsTalk_username");

			if (name != null && name.length() > 0) {
				mav = new ModelAndView("search_friend");
				if (MongoDBConnection.getDB() == null || MongoDBConnection.getMongoClient() == null) {

					if (MongoDBConnection.makeDBConnection(ConstantConfig.DB_IP, ConstantConfig.DB_PORT)) {
						// System.out..println("DB is again connected");
					} else {

						// System.out..println("DB is not connecting.........");
					}
				}

				DBCollection table = MongoDBConnection.getDB().getCollection("tbl_users_detail_data");

				Util util = new Util();
				BasicDBList dbList = util.findFriendList(name);

				List<String> friendsList = new ArrayList<String>();

				if (dbList != null && dbList.size() > 0) {
					for (Object f : dbList) {
						friendsList.add(f.toString());
					}
				}

				List<DBObject> criteria = new ArrayList<DBObject>();
				criteria.add(new BasicDBObject("username", new BasicDBObject("$ne", name)));
				criteria.add(new BasicDBObject("username", Pattern.compile(queryName)));
				BasicDBObject query2 = new BasicDBObject();
				query2.put("is_account_activate", true);
				query2.put("$and", criteria);
				DBCursor cursor = table.find(query2);
				BasicDBObject oneDetails = null;
				List<SearchFriendsPojo> pojoList = new ArrayList<SearchFriendsPojo>();
				while (cursor.hasNext()) {
					oneDetails = (BasicDBObject) cursor.next();
					SearchFriendsPojo pojo = new SearchFriendsPojo();
					pojo.setUsername(oneDetails.getString("username"));
					pojo.setFirstName(oneDetails.getString("first_name"));
					pojo.setLastName(oneDetails.getString("last_name"));
					if (friendsList.contains(oneDetails.getString("username"))) {
						pojo.setFriend(true);
					} else {
						pojo.setFriend(false);
					}

					pojoList.add(pojo);
				}
				mav.addObject("username", name);
				mav.addObject("search_fr", queryName);
				mav.addObject("friendsList", pojoList);
				return mav;
			}

			else {
				mav = new ModelAndView("login");
				mav.addObject("message", "You are logged out!!");
				return mav;

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@RequestMapping(value = "/unFriend", method = RequestMethod.GET)
	public String unFriend(HttpServletRequest request, HttpServletResponse response, @RequestParam String friendName,
			@RequestParam String queryName) throws IOException {

		try {
			HttpSession session = request.getSession();
			String name = (String) session.getAttribute("letsTalk_username");

			if (name != null && name.length() > 0) {

				if (MongoDBConnection.getDB() == null || MongoDBConnection.getMongoClient() == null) {

					if (MongoDBConnection.makeDBConnection(ConstantConfig.DB_IP, ConstantConfig.DB_PORT)) {
						// System.out..println("DB is again connected");
					} else {

						// System.out..println("DB is not connecting.........");
					}
				}
				
				// $addToSet
				
				DBCollection table = MongoDBConnection.getDB().getCollection("tbl_friends_list_data");

				DBObject updateQuery1 = new BasicDBObject();
				updateQuery1.put("$pull", new BasicDBObject("friend_list", friendName));
				
				BasicDBObject query1 = new BasicDBObject();
				query1.put("username", name);
				table.update(query1, updateQuery1, true, true);
				
				DBObject updateQuery2 = new BasicDBObject();
				updateQuery2.put("$pull", new BasicDBObject("friend_list", name));
				
				BasicDBObject query2 = new BasicDBObject();
				query2.put("username", friendName);
				table.update(query2, updateQuery2, true, true);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:" + "/search?queryName=" + queryName;

	}

	@RequestMapping(value = "/addFriend", method = RequestMethod.GET)
	public String addFriend(HttpServletRequest request, HttpServletResponse response, @RequestParam String friendName,
			@RequestParam String queryName) throws IOException {

		try {
			HttpSession session = request.getSession();
			String name = (String) session.getAttribute("letsTalk_username");

			if (name != null && name.length() > 0) {

				if (MongoDBConnection.getDB() == null || MongoDBConnection.getMongoClient() == null) {

					if (MongoDBConnection.makeDBConnection(ConstantConfig.DB_IP, ConstantConfig.DB_PORT)) {
						// System.out..println("DB is again connected");
					} else {

						// System.out..println("DB is not connecting.........");
					}
				}
				DBCollection table = MongoDBConnection.getDB().getCollection("tbl_friends_list_data");

				DBObject updateQuery1 = new BasicDBObject();
				updateQuery1.put("$push", new BasicDBObject("friend_list", friendName));
				BasicDBObject query1 = new BasicDBObject();
				query1.put("username", name);
				table.update(query1, updateQuery1, true, true);
				
				DBObject updateQuery2 = new BasicDBObject();
				updateQuery2.put("$push", new BasicDBObject("friend_list", name));
				BasicDBObject query2 = new BasicDBObject();
				query2.put("username", friendName);
				table.update(query2, updateQuery2, true, true);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:" + "/search?queryName=" + queryName;

	}

	@RequestMapping(value = "/unFriendFromProfile", method = RequestMethod.GET)
	public String unFriendFromProfile(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String queryName) throws IOException {

		try {
			HttpSession session = request.getSession();
			String name = (String) session.getAttribute("letsTalk_username");

			if (name != null && name.length() > 0) {

				if (MongoDBConnection.getDB() == null || MongoDBConnection.getMongoClient() == null) {

					if (MongoDBConnection.makeDBConnection(ConstantConfig.DB_IP, ConstantConfig.DB_PORT)) {
						// System.out..println("DB is again connected");
					} else {

						// System.out..println("DB is not connecting.........");
					}
				}
				DBCollection table = MongoDBConnection.getDB().getCollection("tbl_friends_list_data");
				
				DBObject updateQuery1 = new BasicDBObject();
				updateQuery1.put("$pull", new BasicDBObject("friend_list", queryName));
				
				BasicDBObject query1 = new BasicDBObject();
				query1.put("username", name);
				table.update(query1, updateQuery1, true, true);
				
				DBObject updateQuery2 = new BasicDBObject();
				updateQuery2.put("$pull", new BasicDBObject("friend_list", name));
				
				BasicDBObject query2 = new BasicDBObject();
				query2.put("username", queryName);
				table.update(query2, updateQuery2, true, true);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:" + "/friendProfile?queryName=" + queryName;

	}

	@RequestMapping(value = "/addFriendFromProfile", method = RequestMethod.GET)
	public String addFriendFromProfile(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String queryName) throws IOException {

		try {
			HttpSession session = request.getSession();
			String name = (String) session.getAttribute("letsTalk_username");

			if (name != null && name.length() > 0) {

				if (MongoDBConnection.getDB() == null || MongoDBConnection.getMongoClient() == null) {

					if (MongoDBConnection.makeDBConnection(ConstantConfig.DB_IP, ConstantConfig.DB_PORT)) {
						// System.out..println("DB is again connected");
					} else {

						// System.out..println("DB is not connecting.........");
					}
				}
				DBCollection table = MongoDBConnection.getDB().getCollection("tbl_friends_list_data");

				DBObject updateQuery1 = new BasicDBObject();
				updateQuery1.put("$push", new BasicDBObject("friend_list", queryName));
				BasicDBObject query1 = new BasicDBObject();
				query1.put("username", name);
				table.update(query1, updateQuery1, true, true);
				
				DBObject updateQuery2 = new BasicDBObject();
				updateQuery2.put("$push", new BasicDBObject("friend_list", name));
				BasicDBObject query2 = new BasicDBObject();
				query2.put("username", queryName);
				table.update(query2, updateQuery2, true, true);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:" + "/friendProfile?queryName=" + queryName;

	}

}
