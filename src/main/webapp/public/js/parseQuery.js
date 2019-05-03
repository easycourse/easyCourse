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