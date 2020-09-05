seajs.config({
    alias: {
        'plugins': 'dist/js/plugins',

            /* modules */
        'main': 'default/js/modules/main',
        'authc': 'default/js/modules/authc',
        'sidebox': 'default/js/modules/sidebox',
        'post': 'default/js/modules/post',
        'comment': 'default/js/modules/comment',
        'phiz': 'default/js/modules/phiz',
        'avatar': 'default/js/modules/avatar',
        'editor': 'default/js/modules/editor',
        'view': 'default/js/modules/view',
        'webuploader': 'default/js/modules/webuploader',

        /* vendors */
        'pace': 'vendors/pace/pace.min',
        'pjax': 'vendors/pjax/jquery.pjax',
        'dmuploader': 'vendors/uploader/dmuploader',
        'webuploader.min': 'vendors/webuploader/webuploader.min',
        'webuploader.css': 'vendors/webuploader/webuploader.css',
        'jcrop': 'vendors/jcrop/jquery.jcrop.min',
        'validate': 'vendors/validate/jquery-validate',
        'lazyload': 'vendors/lazyload/jquery.lazyload',
        'share': 'vendors/share.js/js/social-share.min.js',

        'highlight':'vendors/highlight/highlight.pack',
        "tinymce": "vendors/tinymce/tinymce.min",
        "form": "dist/js/jquery.form.min"
    },

    // 路径配置
    paths: {
        'vendors': '//static.uzshare.com' + _base_path + '/dist/vendors',
        'dist': '//static.uzshare.com' + _base_path + '/dist',
        'default': '//static.uzshare.com' + _base_path + '/theme/default'
    },

    // 变量配置
    vars: {
        'locale': 'zh-cn'
    },

    charset: 'utf-8',

    debug: false
});