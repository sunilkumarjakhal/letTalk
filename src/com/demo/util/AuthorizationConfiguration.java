//package com.demo.util;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import com.mongodb.BasicDBObject;
//import com.mongodb.DBCollection;
//import com.mongodb.DBCursor;
//import com.vts.db.MongoDBConnection;
//
//@Configuration
//public class AuthorizationConfiguration extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        // TODO Auto-generated method stub
//        DBCollection dbCollection = MongoDBConnection.getDB().getCollection("tbl_user_auth_api");
//        DBCursor dbCursor = dbCollection.find();
//        InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder> authObject=null;
//        authObject = auth.inMemoryAuthentication();
//        authObject.withUser("fradmin").password("b$!2019").roles("Admin");
//        while (dbCursor.hasNext()) {
//            BasicDBObject b = (BasicDBObject)dbCursor.next();
//            authObject.withUser(b.getString("login_id")).password(b.getString("password")).roles(b.getString("role"));
//        }
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        // TODO Auto-generated method stub
//        http.csrf().disable()
//        .authorizeRequests()
//        .antMatchers("/secure/*").hasRole("Admin")
//        .and().httpBasic().and()
//        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//    }    
//}