
window.onload = function myAlert(){
    console.log("this is onload function");
}

function f1(){
    var n = 99;

    nAdd = function () {
        n += 1;
    }

    function f2(){
        console.log(n);
    }

    return f2;
}

var result = f1();
result();
nAdd();
result();

/*var name = "The Window";
var object = {
    name : "My Object",
    getNameFunc : function(){
        return function(){
            return this.name;
        };
    }
};*/
//alert(object.getNameFunc()());

/*
var name = "The Window";
var object = {
    name : "My Object",
    getNameFunc : function(){
        var that = this;
        return function(){
            return that.name;
        };
    }
};
alert(object.getNameFunc()());*/
var id = 12;
var name = "fanwei";
(function(id, name){
    console.log("这是一个闭包, id 是 "+id +"名称是:" + name);
})(id, name)
