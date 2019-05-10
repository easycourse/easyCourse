function getVariable(variable)
{
    var query = window.location.search;
    if(query.length>0){
        var vars = query.substring(1).split("&");
        for (var i=0;i<vars.length;i++) {
            var pair = vars[i].split("=");
            if(pair[0] == variable){return pair[1];}
        }
    }
    return(false);
}

function parseDate(date){
    var year=d.getFullYear();
    var month=change(d.getMonth()+1);
    var day=change(d.getDate());
    var hour=change(d.getHours());
    var minute=change(d.getMinutes());
    var second=change(d.getSeconds());
    function change(t){
        if(t<10){
            return "0"+t;
        }else{
            return t;
        }
    }
    var time=year+'-'+month+'-'+day+' '+hour+':'+minute+':'+second;
    document.write(time);
}