window.isKey = false;

/*��������*/
document.addEventListener("keydown", function (e) {
    //����ctrl+F5
    if(e.ctrlKey && e.code === 'F5') {
        isKey = true;
        //ɾ������Դ
        localStorage.removeItem('this');
        //���ý���
        $('.layui-this').removeClass('layui-this');
        // $('#seeNews').addClass('layui-this');
    }
});

