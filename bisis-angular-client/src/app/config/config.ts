export class config {

    public static getEnvironmentVariable(value) {
        var environment:string;
        var data = {};
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

            default:
                data = {
                    endPoint: ''
                };
        }
        return data[value];
    }


}