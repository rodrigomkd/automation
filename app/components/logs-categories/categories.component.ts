import { Component } from '@angular/core';
import { Router } from '@angular/router';

//service
import { AppServices } from '../../services/app.services';

@Component({
	selector: 'categories',
	templateUrl: './categories.component.html'
})

export class CategoriesComponent {
  public applications:any = [];
  loaded = false;

  ngOnInit(){
    this.applications = this.appService.retrieveIssuesCategory().subscribe((applications) => {
      this.applications = applications;
      //this.barChartData = this.applications;
      //this.barChartData.length = this.applications.length;
      this.barChartLabels = [];
      this.barChartData = [];

      let dataAlert:any = {};
      dataAlert.data = [];
      dataAlert.label = "ALERT";
      
      let dataError:any = {};
      dataError.data = [];
      dataError.label = "ERROR";

      let dataMaint:any = {};
      dataMaint.data = [];
      dataMaint.label = "MAINTENANCE";

      let dataCritical:any = {};
      dataCritical.data = [];
      dataCritical.label = "CRITICAL";

      let dataNotIssue:any = {};
      dataNotIssue.data = [];
      dataNotIssue.label = "NOT ISSUE";

      for(let app in this.applications) {
        console.log("apps" , this.applications);
        //add chart label for months
        if(! this.barChartLabels.includes(this.applications[app].yyyymm)) {
          this.barChartLabels.push(this.applications[app].yyyymm);
        }
        
        if(this.applications[app].category_name == 'ALERT'){
          dataAlert.data.push(this.applications[app].issues_count);
        }else if(this.applications[app].category_name == 'ERROR'){
          dataError.data.push(this.applications[app].issues_count);
        }else if(this.applications[app].category_name == 'MAINTENANCE'){
          dataMaint.data.push(this.applications[app].issues_count);
        }else if(this.applications[app].category_name == 'NOT ISSUE'){
          dataCritical.data.push(this.applications[app].issues_count);
        }else if(this.applications[app].category_name == 'CRITICAL'){
          dataNotIssue.data.push(this.applications[app].issues_count);
        }        
      }

      this.barChartData.push(dataAlert);
      this.barChartData.push(dataError);
      this.barChartData.push(dataMaint);
      this.barChartData.push(dataCritical);
      this.barChartData.push(dataNotIssue);

      this.loaded = true;
    })
  }

  constructor(private appService: AppServices, private router: Router) {

  }

  public barChartOptions:any = {
    scaleShowVerticalLines: false,
    responsive: true
  };
  public barChartLabels:string[] = ['2006', '2007', '2008', '2009', '2010', '2011', '2012'];
  public barChartType:string = 'bar';
  public barChartLegend:boolean = true;
 
  public barChartData:any[] = [
    {data: [65, 59, 80, 81, 56, 55, 40], label: 'Series A'}
    //{data: [28, 48, 40, 19, 86, 27, 90], label: 'Series B'},
    //{data: [28, 48, 40, 19, 86, 27, 90], label: 'Series C'}
  ];
 
  // events
  public chartClicked(e:any):void {
    console.log(e);
  }
 
  public chartHovered(e:any):void {
    console.log(e);
  }
 
  public randomize():void {
    // Only Change 3 values
    let data = [Math.round(Math.random() * 100),59,80,(Math.random() * 100),56,(Math.random() * 100),40];
    
    
    let clone = JSON.parse(JSON.stringify(this.barChartData));
    console.info(clone);
    clone[0].data = data;
    this.barChartData = clone;
    
    /**
     * (My guess), for Angular to recognize the change in the dataset
     * it has to change the dataset variable directly,
     * so one way around it, is to clone the data, change it and then
     * assign it;
     */
  }
}