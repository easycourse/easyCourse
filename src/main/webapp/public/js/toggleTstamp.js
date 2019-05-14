//时间戳转yyyy-mm-dd hh:ii格式时间
function toggleTstamp(timestamp)
{
//shijianchuo是整数，否则要parseInt转换
    var time = new Date(timestamp);
    var y = time.getFullYear();
    var m = time.getMonth()+1;
    var d = time.getDate();
    var h = time.getHours();
    var mm = time.getMinutes();
    var s = time.getSeconds();
    return y+'-'+add0(m)+'-'+add0(d)+' '+add0(h)+':'+add0(mm)+':'+add0(s);
}

function add0(m){return m<10?'0'+m:m }

//function toggleTstamp(timestamp){
// /*1.重写了toLocaleString方法*/
// Date.prototype.toLocaleString = function() {
//     return this.getFullYear() + "-" +
//         (this.getMonth() + 1) + "-" +
//         this.getDate() + " "
//         + this.getHours() + ":"
//         + this.getMinutes() + ":"
//         + this.getSeconds();
// };
// var timestring=new Date(timestamp);/*传入毫秒数返回东八区中国标准时间*/
// return timestring.toLocaleString();
// }