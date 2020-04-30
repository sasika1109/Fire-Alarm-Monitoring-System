import { Component, OnInit } from '@angular/core';
import { SensorService } from './services/sensor.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'Smoke Detector';
  public sensors;
  public isLoading: boolean = true;

  constructor(private sensorService: SensorService) { }

  ngOnInit() {
    setInterval(() => {
      this.sensorService.getSensorDetails()
        .subscribe((data) => {
          this.isLoading = false;
          this.sensors = data.sensors;
        });
    }, 3000);

  }
}
