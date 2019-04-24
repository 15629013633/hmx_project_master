var menuShow = {
	flag : 1, //屏幕大时   大小菜单切换
	maxFlag : 1, // 屏幕小时   大菜单切换
	minFlag : 1 ,//屏幕小时   小菜单切换
	recordStatus : 1, //记录 大菜单时屏幕  大小菜单的状态
	now_size : 0,
	winSize : 768,
	menUrl : '/loginButton',
	argData: {},
	getHomeData : {},

	logoBox : $('.navbar-header'),
	topMenu : $('.aio-topLeftMenu'),
	leftMenu : $('.aio-leftMenu'),
	contentBox : $('#aio-contentBox'),
	switchBtn : $('.menu-Btn>a'),
	leftMenuUl : $('#accordion'),

	init: function(){
		this.loadMenu(this.menUrl, this.argData);
	},

	initOper : function(){

		this.leftMenuUl.children('li').off('mouseover');
		this.leftMenuUl.children('li').off('mouseout');

		this.now_size = this.getViewportOffset().w;
		if(this.now_size && this.now_size < this.winSize){
			this.initLayout();
			this.recordStatus = 0;
		}
	},

	initLayout : function(){
		this.topMenu.add(this.contentBox).css('margin-left','0');
		this.leftMenu.hide();
		this.headShow();
	},

	loadMenu : function(url, data){ //加载菜单
		var $This = this;
		this.leftMenuUl.getAjaxJSON('post', url, data, function(result, e) {
			var html = '',
				href = '';
			$.each(result.data, function(idx, menuItem) {
				href = menuItem.url.split('/')[1];
				html += '<li class="panel">';
				html += '<a class="collapsed title" href="#'+href+'" url="#'+href+'" data-parent="#accordion" data-toggle="collapse">';
				if(menuItem.icon) {
					html += '<i class="'+ menuItem.icon +'" aria-hidden="true"></i>';
				}
				html += '<span>' + menuItem.buttonName + '</span></a>';
				if(menuItem.permissionModels && menuItem.permissionModels.length > 0){
					html += $This.loadChildMenu(menuItem.id, href, menuItem.permissionModels); //加载子菜单
				}
				html += '</li>';
			});
			e.html(html);

			// var defaultMenu = e.find('li:first-child');
			// defaultMenu.addClass('active').siblings('li').removeClass('active');
			// defaultMenu.find('ul').addClass('in');
			// defaultMenu.find('ul>li:first-child>a').addClass('focus');

			// $This.contentBox.load(defaultMenu.find('ul>li:first-child>a').attr('url'));
			$This.contentBox.load('/home');

			$This.initOper();
			$This.bindEvent();
			// getActiveItem();
		})
	},

	loadChildMenu : function(id, href, childItem){ //加载子菜单
		var html = '<ul id="' + href + '" class="panel-collapse collapse">',
			url = '',
			This = this;
			$.each(childItem, function(idx, menuItem) {
				if(id === menuItem.pid) {
					url = menuItem.url ? menuItem.url.substr(0,menuItem.url.lastIndexOf('/**')) : '#';
					html += '<li><a href="#" url="' + menuItem.url + '">' + menuItem.name + '</a></li>';
					This.getHomeData[menuItem.name] = menuItem.url;
				}else{
					return false;
				}
			})
		html += '</ul>';
		return html;
	},

	bindEvent : function(){
		var $This = this;

		$(window).resize(function(e){
			var w = $This.getViewportOffset().w;
			if(w && 　w < $This.winSize && $This.now_size > $This.winSize) {
				$This.initLayout();
				$This.recordStatus = !$This.flag;
				$This.now_size = w;
			}else if(w && w > $This.winSize && $This.now_size < $This.winSize){
				if($This.flag){
					$This.contentBox.css('margin-left','230px');
					$This.headShow();
				}else{
					$This.contentBox.css('margin-left','50px');
					$This.headHide();
				}
				$This.leftMenu.show();
				$This.now_size = w;
				$This.minFlag = $This.maxFlag = 1; //赋初值
			}
		})

		$This.leftMenuUl.on('click', 'li.panel', function(e) {
			$This.leftMenuUl.find('.active').removeClass('active');
			$(this).addClass('active');
		})

		$This.leftMenuUl.on('click','ul a', function(){
			$This.leftMenuUl.find('.active').removeClass('active');
			$(this).closest('.panel').addClass('active');

			$This.leftMenuUl.find('.focus').removeClass('focus');
			$(this).addClass('focus');
			var url = $(this).attr('url');
			if(url){
				$This.contentBox.load(url);
			}
		})

		$This.switchBtn.on('click', this.switchMenu.bind(this));

	},

	switchMenu : function(e){
		var w = this.getViewportOffset().w;
		if(w && 　w < 768) {
			if(this.recordStatus){
				if(this.minFlag){
					this.minSize(); this.minFlag = 0;
				}else{
					this.initLayout(); this.minFlag = 1;
				}
			}else{
				if(this.maxFlag){
					this.maxSize(); this.maxFlag = 0;
				}else{
					this.initLayout(); this.maxFlag = 1;
				}
			}
		}else{
			if(this.flag){
				this.minMenu(); this.flag = 0;
			}else{
				this.maxMenu(); this.flag = 1;
			}
		}
	},

	minMenu : function(){
		this.leftMenu.add(this.logoBox).width(50);
		this.headHide();
		this.topMenu.css('margin-left', '50px');
		this.minSize();
	},

	headHide:function(){
		this.logoBox.css('padding-left','0px').children('a').fadeOut(10);
	},

	headShow : function(){
		this.logoBox.css('padding-left','13px').children('a').fadeIn(300);
	},

	minSize :function() {
		this.leftMenu.show();

		this.contentBox.css("cssText", "margin-left:50px");

		this.leftMenuUl.find('.title').removeAttr('data-toggle');
		this.labelAHref(true);
		this.leftMenuUl.find('span').css('padding-left', '22px').hide();

		this.leftMenuUl.find('ul').addClass('dropMenu');
		this.leftMenuUl.find('ul a').css('padding-left', '15px');

		var $This = this;
		this.leftMenuUl.children('li').mouseover(function (e) {
			$(this).find('span').add($(this).children('ul')).show();
			$(this).addClass('hover').siblings().removeClass('hover');
			$This.leftMenuUl.width(251);

		}).mouseout(function (e) {
			$(this).find('span').add($(this).children('ul')).hide();
			$(this).removeClass('hover');
			$This.leftMenuUl.width('100%');
		})

		this.leftMenuUl.off('click', 'li.panel');
	},

	maxMenu : function(){
		this.leftMenu.add(this.logoBox).width(230);
		this.headShow();
		this.topMenu.css('margin-left', '230px');
		this.maxSize();
	},

	maxSize : function(){
		this.leftMenu.show();

		this.contentBox.css("cssText", "margin-left:230px");

		this.leftMenuUl.find('ul').removeClass('dropMenu in');
		this.leftMenuUl.find('ul').css('display', '');
		this.leftMenuUl.find('ul a').css('padding-left', '37px');

		this.leftMenuUl.find('span').css('padding-left', '0').fadeIn(500);
		this.leftMenuUl.find('.title').attr('data-toggle', 'collapse');
		this.labelAHref();

		this.leftMenuUl.children('li').unbind('mouseover');
		this.leftMenuUl.children('li').unbind('mouseout');

		var This = this;

		setTimeout(function(){
			This.leftMenuUl.find('.active ul').addClass('in').css('height','');
		},300);
	},

	labelAHref: function(bool){
		this.leftMenuUl.find('.title').each(function(i, item){
			if(bool){
				$(this).attr('href', 'javascript:void(0);');
			}else {
				$(this).attr('href', $(this).attr('url'));
			}
		})
	},

	getViewportOffset : function(){
		if(window.innerWidth) { //IE8及以下不兼容
			return {
				w: window.innerWidth,
				h: window.innerHeight
			}
		} else { //IE8及以下可兼容
			if(document.compatMode === "BackCompat") { //怪异模式
				return {
					w: document.body.clientWidth,
					h: document.body.clientHeight
				}
			} else { //标准模式
				return {
					w: document.documentElement.clientWidth,
					h: document.documentElement.clientHeight
				}
			}
		}
	}

}

$(document).ready(function(){
	menuShow.init();
})


