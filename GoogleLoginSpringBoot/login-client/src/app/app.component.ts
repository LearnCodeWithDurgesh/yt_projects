import { GoogleLoginProvider, GoogleSigninButtonDirective, GoogleSigninButtonModule, SocialAuthService, SocialAuthServiceConfig, SocialLoginModule } from '@abacritt/angularx-social-login';
import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, GoogleSigninButtonModule,SocialLoginModule],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  providers: [
   
  ],
})
export class AppComponent {
  title = 'login-client';

  userInformation: any;

  constructor(private socialAuthService: SocialAuthService,private http: HttpClient) {
      socialAuthService.authState.subscribe(data=>{
        console.log(data);
        

        if(data){

          //make the request to backend:

          this.http.post('http://localhost:8080/auth/login-with-google',{token:data.idToken}).subscribe((response)=>{  
            console.log(response);
            this.userInformation=data
          })

        }

        
      })
  }

  googleLoginResponse(response:any){
    console.log(response)
  }
}
