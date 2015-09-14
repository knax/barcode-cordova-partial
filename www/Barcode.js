(function () {
    var exec = require('cordova/exec');

    function Barcode() {
        console.log("Barcode.js: is created");
    }

    Barcode.prototype.showToast = function (aString) {
        console.log("Barcode.js: showToast");
        exec(function (result) { /*alert("OK" + reply);*/
        }, function (result) { /*alert("Error" + reply);*/
        }, "Barcode", "showMessage", [aString]);
    };

    Barcode.prototype.initializeCamera = function () {
        return new Promise(function (resolve, reject) {
            console.log("Barcode.js: initialize Camera");
            exec(function (result) {
                resolve(result);
            }, function (error) {
                reject(error);
            }, "Barcode", "initializeCamera", []);
        });
    };

    Barcode.prototype.execute = function (name, argumentArray) {
        return new Promise(function (resolve, reject) {
            console.log("Barcode.js: executing " + name + " with argument " + argumentArray);
            exec(function (result) {
                resolve(result);
            }, function (error) {
                reject(error);
            }, "Barcode", name, argumentArray);
        });
    };



    module.exports = new Barcode();
})();