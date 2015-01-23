AppListPlugin = {
    getAppList: function(successCallback, errorCallback) {
        cordova.exec(
            successCallback, // success callback function
            errorCallback, // error callback function
            'AppListPlugin', // mapped to our native Java class called "CalendarPlugin"
            'getAppList', // with this action name
            []
        ); 
    }
}