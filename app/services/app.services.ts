import { Observable } from "rxjs/Rx";
import { Injectable } from "@angular/core";
import { Http, Response } from "@angular/http";
import { pipe } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AppServices {
  serviceURL:string = "http://nomada.com.mx/apps/manager-logs/js/services/";

  constructor(private http : Http) {}

  retrieveApplications() {
    //return this.http.get(this.serviceURL + "getApplications.php").map((res: Response) => res.json());

    return this.http.get(this.serviceURL + "getApplications.php")
    .pipe(
        map((res: Response) => res.json())
    );

    //let response = [];
  	//response = this.http.get( this.serviceURL + "getApplications.php").subscribe( response => console.log(response); return response; );
  	//return response;
  	//return this.http.get( this.serviceURL + "getApplications.php").pipe(map(this.extractData))
      //  return res.json();
    //});

      //return this.http.get(this.serviceURL + "getApplications.php")
		//	.then( function(res) {
		//	return res.data;
		//},function(err){
		//	alert("ERROR: " + err);
		//	console.log(err)
		//})
  }

  retrieveIssuesCategory() {
    return this.http.get(this.serviceURL + "getIssuesCategory.php")
    .pipe(
        map((res: Response) => res.json())
    );
  }
}
