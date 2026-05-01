package com.lcwd.google.login.controllers

import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.http.apache.v2.ApacheHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.lcwd.google.login.GoogleLoginRequest
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
class AuthController {


    val logger = LoggerFactory.getLogger(AuthController::class.java)

    //post mapping for google login:

    @PostMapping("/login-with-google")
    fun googleLogin(@RequestBody googleLoginRequest: GoogleLoginRequest): Map<String, String> {
        logger.info("Login with google :")
        logger.info("token {}", googleLoginRequest.token)

        //varify token

        val verifier = GoogleIdTokenVerifier.Builder(ApacheHttpTransport(), GsonFactory()).setAudience(listOf("691628430738-hpq01a18o2rmb3le7v6otgqc2lenqjcd.apps.googleusercontent.com")).build()

        val verify = verifier.verify(googleLoginRequest.token)
        if (verify != null) {
            logger.info("Token verified")
            //get data from token and api
            val email = verify.payload.email
            logger.info("Email {}", email)
            val name = verify.payload["name"]
            val pictureUrl = verify.payload["picture"]
            val locale = verify.payload["locale"]
            val familyName = verify.payload["family_name"]
            val givenName = verify.payload["given_name"]
            logger.info("name {}", name)
            logger.info("pictureUrl {}", pictureUrl)
            logger.info("locale {}", locale)
            logger.info("familyName {}", familyName)
            logger.info("givenName {}", givenName)


            return mapOf(
                    Pair("name", name.toString()),
                    Pair("email", email),
                    Pair("givenName", givenName.toString()),
                    Pair("picture", pictureUrl.toString())

            )
        } else {
            logger.info("Invalid Token")
            return mapOf(Pair("message", "Invalid Token !!"));
        }

    }

}