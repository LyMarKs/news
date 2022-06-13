window.isKey = false;

/*¼àÌı¼üÅÌ*/
document.addEventListener("keydown", function (e) {
    //¼àÌıctrl+F5
    if(e.ctrlKey && e.code === 'F5') {
        isKey = true;
        //É¾³ı½¹µãÔ´
        localStorage.removeItem('this');
        //ÉèÖÃ½¹µã
        $('.layui-this').removeClass('layui-this');
        // $('#seeNews').addClass('layui-this');
    }
});

