//读取cookie，需要注意的是cookie是不能存中文的，如果需要存中文，解决方法是后端先进行编码encode()，前端取出来之后用decodeURI('string')解码。（安卓可以取中文cookie，IOS不行）
function getCookie(name) {
        var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
        if (arr = document.cookie.match(reg)){
        return true;
       // return (arr[2]);
      }else{
      return false
     }
}

//设置cookie   name为cookie的名字，value是值，expiredays为过期时间（天数）
function setCookie (name, value, expiredays) {
     var exdate = new Date();
     exdate.setDate(exdate.getDate() + expiredays);
    document.cookie = c_name + "=" + escape(value) + ((expiredays == null) ? "" : ";expires=" + exdate.toGMTString());
}

 //删除cookie

function delCookie (name) {
      var exp = new Date();
      exp.setTime(exp.getTime() - 1);
      var cval = getCookie(name);
     if (cval != null)
     document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString();
}