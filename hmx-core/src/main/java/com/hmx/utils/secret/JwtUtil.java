package com.hmx.utils.secret;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.hmx.utils.result.LoginUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
	
	private static final String JJWT_SECURITY_KEY="hmx.com";
	private static final Long TTL_MILLIS= 0L;
	
	
	@Autowired
	Gson gson;
	
	/**
     * 创建jwt
     * @param id
     * @param subject
     * @param ttlMillis
     * @return
     * @throws Exception
     */
    public String createJWT( LoginUser user )  {

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        String json = gson.toJson(user);
        
        JwtBuilder builder = Jwts.builder()
            .setIssuedAt(now)
            .setSubject( AESOperator.getInstance().encrypt( json ))
            .signWith(signatureAlgorithm, JJWT_SECURITY_KEY);
        if (TTL_MILLIS > 0) {
            long expMillis = nowMillis + TTL_MILLIS;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        return builder.compact();
    }

    /**
     * 解密jwt
     * @param jwt
     * @return
     * @throws Exception
     */
    public Claims parseJWT(String jwt) throws Exception{
        Claims claims = Jwts.parser()         
           .setSigningKey(JJWT_SECURITY_KEY)
           .parseClaimsJws(jwt).getBody();
        return claims;
    }
    
    
    public static void main(String[] args) throws Exception {
//    	JwtUtil jwtUtil = new JwtUtil();
//    	String createJWT = jwtUtil.createJWT("1", "zhangsan",1);
//    	
//    	System.out.println(createJWT);
//    	
//    	Claims parseJWT = jwtUtil.parseJWT("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxIiwiaWF0IjoxNTAwNzEyMDQ1LCJzdWIiOiJ3T1pkOHRVeGgwUm5Nd3A2N3ZoaGJBPT0ifQ.HSYzUWwDu3BCP5OGmkByfIowtRRrjvfNwuZXDworG1A");
//    	long expMillis = 3L*1000L + TTL_MILLIS;
//        Date exp = new Date(expMillis);
//    	parseJWT.setExpiration(exp);
//    	
//    	String id = parseJWT.getId();
//    	String subject = parseJWT.getSubject();
//    	
//    	System.out.println();
//    	
//    	System.out.println( id + " " + subject );
    	
	}
    

}
