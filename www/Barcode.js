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

    Barcode.prototype.releaseCamera = function () {
        return new Promise(function (resolve, reject) {
            console.log("Barcode.js: release camera");
            exec(function (result) {
                resolve(result);
            }, function (error) {
                reject(error);
            }, "Barcode", "releaseCamera", []);
        })
    };

    Barcode.prototype.setViewfinderDimension = function (dimension) {
        dimension = dimension || {};
        dimension.x = dimension.x || 0;
        dimension.y = dimension.y || 0;
        dimension.width = dimension.width || 0;
        dimension.height = dimension.height || 0;

        return new Promise(function (resolve, reject) {
            console.log("Barcode.js: set viewfinder");
            exec(function (result) {
                resolve(result);
            }, function (error) {
                reject(error);
            }, "Barcode", "setViewfinderDimension", [dimension.x, dimension.y, dimension.width, dimension.height]);
        })
    };

    Barcode.prototype.startCamera = function () {
        return new Promise(function (resolve, reject) {
            console.log("Barcode.js: start Camera");
            exec(function (result) {
                resolve(result);
            }, function (error) {
                reject(error);
            }, "Barcode", "startCamera", []);
        });
    };

    Barcode.prototype.stopCamera = function () {
        return new Promise(function (resolve, reject) {
            console.log("Barcode.js: stop Camera");
            exec(function (result) {
                resolve(result);
            }, function (error) {
                reject(error);
            }, "Barcode", "stopCamera", []);
        });
    };

    Barcode.prototype.getCameraObject = function () {
        return new Promise(function (resolve, reject) {
            console.log("Barcode.js: get camera object");
            exec(function (result) {
                resolve(result);
            }, function (error) {
                reject(error);
            }, "Barcode", "getCameraObject", []);
        })
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

    Barcode.prototype.getBarcode = function () {
        return new Promise(function (resolve, reject) {
            console.log("Barcode.js: get barcode");
            exec(function (result) {
                resolve(result);
            }, function (error) {
                reject(error);
            }, "Barcode", "getBarcode", []);
        });
    };


    module.exports = new Barcode();
})();