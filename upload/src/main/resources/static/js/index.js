layui.use(['upload', 'form', 'element'], function () {
    var $ = layui.jquery
        , upload = layui.upload
        , form = layui.form;
    var element = layui.element;
    upload.render({
        elem: '#uploadImage'
        , url: '/upload'
        , accept: 'images'
        , size: 5 * 1024
        , done: function (res) {
            if (res.code == 0) {
                layer.msg('上传成功');
                layui.$('#uploadDemoView').removeClass('layui-hide').find('img').attr('src', res.url);
                layui.$('#uploadUrl').find('input').attr('value', res.url);
                layui.$('.btn').attr('data-clipboard-text', res.url);
            }
        }
    });

    form.on('submit(imageForm)', function (data) {
        var mark = data.field['mark'];
        var url = data.field['url'];
        $.ajax({
            type: 'post',
            url: '/saveFile',
            data: {
                "mark": mark,
                "url": url,
                "type": "IMAGE",
            },
            dataType: 'JSON',
            success: function (data) {
                if (data.code == 0) {
                    layui.$('#uploadDemoView').addClass('layui-hide');
                    layui.$('.btn').click();
                    layer.msg('上传成功，URL已复制到剪切板');
                    $('#imageForm')[0].reset();
                } else {
                    layer.alert(data.msg);
                }
            }
        });
        return false;
    });
});
var clipboard = new ClipboardJS('.btn');

