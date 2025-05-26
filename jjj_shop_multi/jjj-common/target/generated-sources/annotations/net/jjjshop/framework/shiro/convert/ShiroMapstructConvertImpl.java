package net.jjjshop.framework.shiro.convert;

import javax.annotation.Generated;
import net.jjjshop.framework.shiro.jwt.JwtToken;
import net.jjjshop.framework.shiro.vo.JwtTokenRedisVo;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-26T16:19:13+0800",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 1.8.0_202 (Oracle Corporation)"
)
public class ShiroMapstructConvertImpl implements ShiroMapstructConvert {

    @Override
    public JwtTokenRedisVo jwtTokenToJwtTokenRedisVo(JwtToken jwtToken) {
        if ( jwtToken == null ) {
            return null;
        }

        JwtTokenRedisVo jwtTokenRedisVo = new JwtTokenRedisVo();

        jwtTokenRedisVo.setHost( jwtToken.getHost() );
        jwtTokenRedisVo.setUsername( jwtToken.getUsername() );
        jwtTokenRedisVo.setSalt( jwtToken.getSalt() );
        jwtTokenRedisVo.setToken( jwtToken.getToken() );
        jwtTokenRedisVo.setCreateDate( jwtToken.getCreateDate() );
        jwtTokenRedisVo.setExpireSecond( jwtToken.getExpireSecond() );
        jwtTokenRedisVo.setExpireDate( jwtToken.getExpireDate() );

        return jwtTokenRedisVo;
    }
}
