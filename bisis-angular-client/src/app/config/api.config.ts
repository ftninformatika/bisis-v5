export class ApiConfig {

    public static Origin: string = ApiConfig.getOrigin();

    public static getOrigin() {
        const environment: string = window.location.hostname;
        switch (environment) {
            case'localhost': return '';
            case 'app.bisis.rs': return 'bisisWS/';
            case 'test.bisis.rs': return 'bisisWS/';
            default: return '';
        }
    }
}
