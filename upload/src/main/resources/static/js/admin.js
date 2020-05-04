layui.use(['table', 'form', 'element'], function () {
    var table = layui.table;
    var element = layui.element;
    var form = layui.form;
    //第一个实例
    var adminTable = table.render({
        elem: '#admin'
        , url: '/list/' //数据接口
        , page: true //开启分页
        , toolbar: true
        , cols: [[ //表头
            {field: 'mark', title: '文件描述', align: 'center', width: 200}
            , {field: 'type', title: '文件类型', align: 'center', width: 150}
            , {field: 'createTime', title: '上传时间', align: 'center', width: 200}
            , {field: 'url', title: '地址', align: 'center', width: 200}
            , {fixed: 'right', title: '操作', align: 'center', width: 200, toolbar: '#barDemo'}
        ]]
    });

    form.on('submit(search)', function (data) {
        var type = data.field['type'];
        var mark = data.field['mark'];
        adminTable.reload({
            where: {mark: mark, type: type}, page: {curr: 1}
        });
        return false;
    });

    table.on('tool(admin)', function (obj) {
        var data = obj.data
            , layEvent = obj.event;
        if (layEvent === 'download') {
            window.open(data.url);
        } else if (layEvent === 'copy') {
            layui.$('.btn').attr('data-clipboard-text', data.url);
            layui.$('.btn').click();
            layer.msg('URL已复制到剪切板');
        } else if (layEvent === 'delete') {
            layer.confirm('确定删除吗', function (index) {
                layer.close(index);
                $.ajax({
                    async: false,
                    type: 'post',
                    url: '/delete',
                    data: {
                        'id': data.id,
                    },
                    dataType: 'JSON',
                    success: function (data) {
                        adminTable.reload();
                        layer.msg(data.msg);
                    }
                });
            });
        }
    });
});

layui.use('element', function () {
    var element = layui.element; //导航的hover效果、二级菜单等功能，需要依赖element模块
    //监听导航点击
});
// 引用ClipboardJS
var clipboard = new ClipboardJS('.btn');