package com.arriendosreal.webapp.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import java.util.ArrayList;
import java.util.List;


import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.Map;

import com.arriendosreal.webapp.entities.Users;
import com.arriendosreal.webapp.repositories.UsersRepository;

@RestController
public class Login {
	
	@Autowired
	private UsersRepository userRepo;
	
	//Test
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	private SimpleJdbcCall simpleJdbcCallRefCursor;
	
	@PostConstruct
    public void init() {
        // o_name and O_NAME, same
        jdbcTemplate.setResultsMapCaseInsensitive(true);

        // Convert o_c_book SYS_REFCURSOR to List<Book>
        simpleJdbcCallRefCursor = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("SP_GET_USERS")
                .returningResultSet("out_users",
                        BeanPropertyRowMapper.newInstance(Users.class));

    }
	
	List<Users> findUserById(int user_id) {

        SqlParameterSource paramaters = new MapSqlParameterSource()
                .addValue("in_user_id", user_id);

        Map out = simpleJdbcCallRefCursor.execute(paramaters);

        if (out == null) {
            return Collections.emptyList();
        } else {
            return (List) out.get("out_users");
        }

    }
		
	@PostMapping(value = "/login", produces = "application/json; charset=utf-8")
	public ResponseEntity<String> login(@RequestParam(name="email", required=true) String email, @RequestParam(name="password", required=true) String password, Model model) {
		
		Users user = userRepo.findByEmail(email);
		if(user != null ) {
			if(user.getPassword().contentEquals(password.toString())) {
				String json = String.format("{\"email\": \"%s\", \"profile\": \"%s\"}", user.getEmail(), user.getProfiles().getProfileName());
				return new ResponseEntity<>(json, HttpStatus.OK	);
			} else {
				return new ResponseEntity<>("Wrong creds", HttpStatus.UNAUTHORIZED);
			}			
		} else {
			return new ResponseEntity<>("NPE!", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@PostMapping(value = "/user", produces = "application/json; charset=utf-8")
	public ResponseEntity<String> createUser(@RequestParam(name="profileId", required=true) int profileId,
			@RequestParam(name="username", required=true) String username, 
			@RequestParam(name="email", required=true) String email, 
			@RequestParam(name="password", required=true) String password) {
		
		int resultado = 0;
		try {
			resultado = userRepo.createUser(username, email, password, profileId);
		} catch (Exception e) {
			System.out.println(e.toString());
		}

		if(resultado > 0) {
			return new ResponseEntity<>("OK", HttpStatus.OK	);			
		} else {
			return new ResponseEntity<>("NPE!", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	/*Not working, need to figure out why it's producing a null*/
	@GetMapping(value = "/user", produces = "application/json; charset=utf-8")
	public ResponseEntity<String> getUserByID(@RequestParam(name="userId", required=true) int user_id) {		
	
		Users user = new Users();
		List<Users> users = new ArrayList<Users>();
		Users user1 = new Users();
		
		user = userRepo.findById(user_id).orElse(null);
		try {
			users = findUserById(user_id);
			user = users.get(0);
		} catch (Exception e) {
			System.out.println(e.toString());
		}

		if(user != null) {
			return new ResponseEntity<>("OK", HttpStatus.OK	);
		} else {
			return new ResponseEntity<>("NPE!", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
