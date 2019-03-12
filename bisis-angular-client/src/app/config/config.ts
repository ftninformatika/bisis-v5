export class Config {

    public static getEnvironmentVariable(value) {
        let environment: string;
        let data = {};
        environment = window.location.hostname;
        switch (environment) {
            case'localhost':
                data = {
                    endPoint: ''
                };
                break;
            case 'app.bisis.rs':
                data = {
                    endPoint: 'bisisWS/'
                };
                break;
            case 'test.bisis.rs':
                data = {
                    endPoint: 'bisisWS/'
                };
                break;
            default:
                data = {
                    endPoint: ''
                };
        }
        return data[value];
    }
}
