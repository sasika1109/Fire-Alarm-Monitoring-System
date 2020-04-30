import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({ providedIn: "root" })
export class SensorService {

    constructor(private http: HttpClient) { }

    getSensorDetails() {
        return this.http.get<{ message: string, sensors: any }>('http://localhost:3000/api/sensors/');
    }
} 