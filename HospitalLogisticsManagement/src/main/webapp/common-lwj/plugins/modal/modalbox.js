// $(document).ready(function(){
var ModalBox = function(caption){
    this.title = (caption.title == undefined) ?  '你好' : caption.title;
    this.url = (caption.url == undefined) ?  '' : caption.url;
    this.width = (caption.width == undefined) ? 800 : caption.width;
    this.height = (caption.height == undefined) ? 500 : caption.height;
}
ModalBox.prototype = {
    constructor: ModalBox,
    bg: function(){
        window.parent.$('body').append('<div class="bg"></div>');
        window.parent.$('.bg').css({
            'background-color': 'black',
            'opacity': '0.7',
            'width': '100%',
            'height': '100%',
            'position': 'absolute',
            'top': 0,
            'left': 0,
            'z-index': 9999
        })
    },
    modalPop:function(){
        var domHeight = window.parent.$('body').height()
        var domWidth = window.parent.$('body').width()
        window.parent.$('body').append('<div class="modalPop animated zoomIn">'+
            '<p class="title"><span class="title-left">'+ this.title +'</span><span class="closeModal"><i class="fa fa-close"></i></span></p>'+
            '<div class="modalMain"><iframe width="100%" height="100%" border-radius="8px" marginheight=0 marginwidth=0  src="' + this.url +' " frameborder="0"></iframe>'+
            '</div></div>');
        window.parent.$('.modalPop').css({
            'position': 'absolute',
            'box-sizing': 'border-box',
            'background-color': 'white',
            'border-radius': '3px',
            'padding': '16px',
            'z-index': 99999,
            'width': this.width,
            'height': this.height,
            'top': (domHeight - this.height) / 2,
            'left': (domWidth - this.width) / 2,
            'border': 'rgba(0,0,0,0.8)',
            'box-shadow': '0 0 30px #222222'
        })
        window.parent.$('.closeModal').css({
            'float': 'right',
            'font-size': '16px'
        })
        window.parent.$('.modalMain').css({'height': '93%'})
        var self = this;
        window.parent.$('.closeModal').click(function(){
            self.closeModal()
        })
    },
    closeModal:function(){
        window.parent.$('.modalPop,.bg').fadeOut('slow',function () {
            window.parent.$('.bg').remove();
            window.parent.$('.modalPop').remove()
        })
    },
    init:function(){
        this.bg();
        this.modalPop();
    }
}

// return ModalBox;
// })