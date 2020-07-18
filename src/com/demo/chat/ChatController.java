package com.demo.chat;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.demo.search.friends.SearchFriendsPojo;
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
public class ChatController {

	@RequestMapping(value = "/chat", method = RequestMethod.GET)
	public ModelAndView register(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView mav = new ModelAndView("chat");

		HttpSession session = request.getSession();
		String name = (String) session.getAttribute("letsTalk_username");

		if (name != null && name.length() > 0) {
			mav = new ModelAndView("chat");
			if (MongoDBConnection.getDB() == null || MongoDBConnection.getMongoClient() == null) {

				if (MongoDBConnection.makeDBConnection(ConstantConfig.DB_IP, ConstantConfig.DB_PORT)) {
					// System.out..println("DB is again connected");
				} else {

					// System.out..println("DB is not connecting.........");
				}
			}

			DBCollection table = MongoDBConnection.getDB().getCollection("tbl_users_detail_data");

			BasicDBObject query1 = new BasicDBObject();
			query1.put("username", name);

			List<String> friendsList = new ArrayList<String>();

			Util util = new Util();
			BasicDBList dbList = util.findFriendList(name);

			if (dbList.size() > 0) {
				for (Object f : dbList) {
					friendsList.add(f.toString());
				}
			}

			BasicDBObject query = new BasicDBObject();
			query.put("is_account_activate", true);
			query.put("username", new BasicDBObject("$in", dbList));

			List<SearchFriendsPojo> allFriendList = new ArrayList<SearchFriendsPojo>();

			DBCursor cursor = table.find(query);
			while (cursor.hasNext()) {
				BasicDBObject oneDetails = (BasicDBObject) cursor.next();
				SearchFriendsPojo friendPojo = new SearchFriendsPojo();
				friendPojo.setUsername(oneDetails.getString("username"));
				friendPojo.setFirstName(oneDetails.getString("first_name"));
				friendPojo.setLastName(oneDetails.getString("last_name"));

				allFriendList.add(friendPojo);

			}

			mav.addObject("allFriendList", allFriendList);
			return mav;
		}

		else {
			mav = new ModelAndView("login");
			mav.addObject("message", "You are logged out!!");
			return mav;

		}

	}

	@RequestMapping(value = "/messages", method = RequestMethod.GET)
	public ModelAndView getMessages(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String fid) {

		ModelAndView mav = new ModelAndView("messages");

		HttpSession session = request.getSession();

		String name = (String) session.getAttribute("letsTalk_username");
		session.setAttribute("letsTalk_friend_name", fid);

		System.out.println((String) session.getAttribute("letsTalk_friend_name"));

		if (name != null && name.length() > 0) {
			mav = new ModelAndView("messages");
			if (MongoDBConnection.getDB() == null || MongoDBConnection.getMongoClient() == null) {

				if (MongoDBConnection.makeDBConnection(ConstantConfig.DB_IP, ConstantConfig.DB_PORT)) {
					// System.out..println("DB is again connected");
				} else {

					// System.out..println("DB is not connecting.........");
				}
			}

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

			DBCollection table = MongoDBConnection.getDB().getCollection("tbl_chat_data");

			BasicDBObject query1 = new BasicDBObject("friends", name + "-" + fid);
			BasicDBObject query2 = new BasicDBObject("friends", fid + "-" + name);
			BasicDBList orQuery = new BasicDBList();
			orQuery.add(query1);
			orQuery.add(query2);
			BasicDBObject query = new BasicDBObject("$or", orQuery);

			List<UserMessagesDetailPojo> allMessageList = new ArrayList<UserMessagesDetailPojo>();

			DBCursor cursor = table.find(query);

			while (cursor.hasNext()) {
				BasicDBObject oneDetails = (BasicDBObject) cursor.next();
				UserMessagesDetailPojo messagePojo = null;
				BasicDBList dbList = new BasicDBList();

				dbList = (BasicDBList) oneDetails.get("chat");

				for (Object element : dbList) {
					messagePojo = new UserMessagesDetailPojo();
					messagePojo.setName(((BasicDBObject) element).getString("name"));
					messagePojo.setMessage(((BasicDBObject) element).getString("message"));
					messagePojo.setDateTime(sdf.format(((BasicDBObject) element).getDate("time")));

					allMessageList.add(messagePojo);
				}

			}

			mav.addObject("allMessageList", allMessageList);
			mav.addObject("fid", fid);
			mav.addObject("username", name);
			mav.addObject("sendMessagePojo", new Message());

			return mav;
		}

		else {
			mav = new ModelAndView("login");
			mav.addObject("message", "You are logged out!!");
			return mav;

		}

	}

	@RequestMapping(value = "/addMessages", method = RequestMethod.POST)
	public String addMessages(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("sendMessagePojo") Message sendMessagePojo) throws ParseException, IOException {

		HttpSession session = request.getSession();
		String name = (String) session.getAttribute("letsTalk_username");
		String friendName = (String) session.getAttribute("letsTalk_friend_name");
		// List<UserMessagesDetailPojo> allMessageList = new
		// ArrayList<UserMessagesDetailPojo>();

		if (name != null && name.length() > 0) {

			if (MongoDBConnection.getDB() == null || MongoDBConnection.getMongoClient() == null) {

				if (MongoDBConnection.makeDBConnection(ConstantConfig.DB_IP, ConstantConfig.DB_PORT)) {
					// System.out..println("DB is again connected");
				} else {

					// System.out..println("DB is not connecting.........");
				}
			}

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

			DBCollection table = MongoDBConnection.getDB().getCollection("tbl_chat_data");

			BasicDBObject query1 = new BasicDBObject("friends", name + "-" + friendName);
			BasicDBObject query2 = new BasicDBObject("friends", friendName + "-" + name);
			BasicDBList orQuery = new BasicDBList();
			orQuery.add(query1);
			orQuery.add(query2);
			BasicDBObject query = new BasicDBObject("$or", orQuery);

			BasicDBObject messageData = new BasicDBObject();
			messageData.put("name", name);
			messageData.put("message", sendMessagePojo.getMessage());
			
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.HOUR, 5);
			cal.add(Calendar.MINUTE, 30);

			messageData.put("time", sdf.parse(sdf.format(cal.getTime())));

			BasicDBObject update = new BasicDBObject();
			update.put("$push", new BasicDBObject("chat", messageData));

			table.update(query, update, true, true);

//			DBCursor cursor = table.find(query);
//
//			while (cursor.hasNext()) {
//				BasicDBObject oneDetails = (BasicDBObject) cursor.next();
//				UserMessagesDetailPojo messagePojo = null;
//				BasicDBList dbList = new BasicDBList();
//
//				dbList = (BasicDBList) oneDetails.get("chat");
//
//				for (Object element : dbList) {
//					messagePojo = new UserMessagesDetailPojo();
//					messagePojo.setName(((BasicDBObject) element).getString("name"));
//					messagePojo.setMessage(((BasicDBObject) element).getString("message"));
//					messagePojo.setDateTime(sdf.format(((BasicDBObject) element).getDate("time")));
//
//					allMessageList.add(messagePojo);
//				}
//
//			}
//
//		}
//
//		Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
//		String mapData = prettyGson.toJson(allMessageList);
//
//		response.getWriter().write(mapData);

			return ("redirect:/messages?fid=" + friendName);
		}
		return ("redirect:/messages?fid=" + friendName);
	}
}

class Message {
	public String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
