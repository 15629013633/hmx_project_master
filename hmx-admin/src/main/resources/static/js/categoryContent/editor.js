

var editorValue = (function () {
    var initWangEditor = function(){
        var E = window.wangEditor
        editor = new E('#editor');
        // 或者 var editor = new E( document.getElementById('editor') )
        // 自定义配置颜色（字体颜色、背景色）
        editor.customConfig.colors = [
            '#000000',
            '#eeece0',
            '#1c487f',
            '#4d80bf',
            '#c24f4a',
            '#8baa4a',
            '#7b5ba1',
            '#46acc8',
            '#f9963b',
            '#ffffff'
        ];
        // 表情面板可以有多个 tab ，因此要配置成一个数组。数组每个元素代表一个 tab 的配置
        editor.customConfig.emotions = [
            {
                // tab 的标题
                title: '默认',
                // type -> 'emoji' / 'image'
                type: 'image',
                // content -> 数组
                content: [
                    {
                        alt: '[坏笑]',
                        src: 'http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/50/pcmoren_huaixiao_org.png'
                    },
                    {
                        alt: '[舔屏]',
                        src: 'http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/40/pcmoren_tian_org.png'
                    }
                ]
            },
            {
                // tab 的标题
                title: 'emoji',
                // type -> 'emoji' / 'image'
                type: 'emoji',
                // content -> 数组
                content: ['😀', '😃', '😄', '😁', '😆']
            }
        ];

        // 隐藏“网络图片”tab
        editor.customConfig.showLinkImg = false;
        // 使用 base64 保存图片
        editor.customConfig.uploadImgShowBase64 = true;

        editor.customConfig.onfocus = function () {
            // $('#editor').find('.placeholder').remove();
        }

        editor.create();
    }
    return {
        initEditor:function(){
            initWangEditor();
        },
        getValue:function(){
            return editor.txt.html()
        }
    }
    // $(document).ready(function(){
    //     initWangEditor();
    // })
}())

editorValue.initEditor()