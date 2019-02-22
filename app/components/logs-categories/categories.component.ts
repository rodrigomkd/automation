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
    this.applications = this.appService.retrieveApplications().subscribe((applications) => {

            // do stuff with our data here.
            // ....

            // asign data to our class property in the end
            // so it will be available to our template
            //console.log("applications", applications);
            this.applications = applications;
            this.barChartData = this.applications;
            this.barChartData.length = this.applications.length;
            for(let app in this.applications){
              console.log("applications", app);
        let application = {};
        application.data = [10,20,30,40,50,60,70];
        application.label = this.applications[app].app_name;
        this.barChartData[app] = application;
        
    }
    this.loaded = true;
            //this.randomize();
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