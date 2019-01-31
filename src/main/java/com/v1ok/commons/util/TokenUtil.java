package com.v1ok.commons.util;

import com.v1ok.commons.Head;
import com.v1ok.commons.IContext;
import com.v1ok.commons.IUserContext;
import com.v1ok.commons.impl.DefaultUserContext;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import java.util.Date;
import java.util.List;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Created by liubinduo on 2017/5/17.
 */
@Slf4j
public class TokenUtil {

  private static final String TOKEN = "token";
  private static final String USER_ROLES_ID_KEY = "rules";
  private static final String USER_ID_KEY = "userId";
  private static final String USER_TERRITORY_ID_KEY = "territories";
  private static final String USER_PERMISSIONS_ID_KEY = "permissions";

  public static String getToken(Head head) {
    String token;
    //请求体里的token优先级最高
    if (head != null) {
      token = head.getToken();
      if (StringUtils.isNoneBlank(token)) {
        return token;
      }
    }

    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
        .getRequestAttributes()).getRequest();

    if (request != null) {

      //其次是url上的参数
      token = request.getParameter(TOKEN);
      if (StringUtils.isNotEmpty(token)) {
        return token;
      }

      //最后浏览器的head头中的token
      token = request.getHeader(TOKEN);
      if (StringUtils.isNotEmpty(token)) {
        return token;
      }
    }

    return null;
  }

  public static String createToken(IContext context, String base64Security) {
    return createToken(context, base64Security, DateUtils.addDays(new Date(), 60));
  }

  public static String createToken(IContext context, String base64Security, Date expiration) {
    SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(base64Security);
    Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

    IUserContext userContext = context.currentUser();

    JwtBuilder builder = Jwts.builder().
        setHeaderParam("typ", "JWT").
        claim(USER_ROLES_ID_KEY, userContext.getRoles()).
        claim(USER_ID_KEY, userContext.getUserId()).
        claim(USER_TERRITORY_ID_KEY, userContext.getTerritories()).
        claim(USER_PERMISSIONS_ID_KEY, userContext.getPermissions()).
        signWith(signatureAlgorithm, signingKey)
        .setExpiration(expiration);

    return builder.compact();
  }

  public static IUserContext parseToken(String jsonWebToken, String key) {
    try {
      Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(key))
          .parseClaimsJws(jsonWebToken).getBody();

      if (claims != null) {

        List<Long> roles = claims.get(TokenUtil.USER_ROLES_ID_KEY, List.class);
        Object userIdObj = claims.get(TokenUtil.USER_ID_KEY);
        Long userId = NumberUtils.toLong(userIdObj == null ? "0" : userIdObj.toString());
        List<Long> territories = claims.get(TokenUtil.USER_TERRITORY_ID_KEY, List.class);
        List<String> permissions = claims.get(TokenUtil.USER_PERMISSIONS_ID_KEY, List.class);

        return DefaultUserContext.builder()
            .userId(userId)
            .territories(territories)
            .roles(roles).permissions(permissions).build();
      }
    } catch (Exception e) {
      log.error("The token[" + jsonWebToken + "] is error!", e);
    }

    return null;
  }

}
