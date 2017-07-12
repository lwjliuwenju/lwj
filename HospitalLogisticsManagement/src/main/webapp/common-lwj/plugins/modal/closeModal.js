function closeModalRefresh() {
    $(window.parent.document).contents().find('#main-iframe').contents().find('button[name=refresh]').click();
    // window.parent.$('.modalPop,.bg').fadeOut('slow',function () {
        $(window.parent.document).contents().find('.bg').remove();
        $(window.parent.document).contents().find('.modalPop').remove();
        // window.parent.$('.bg').remove();
        // window.parent.$('.modalPop').remove()
    // })
}
function closeModalRefreshCount() {
    // console.log('执行到这里')
    console.log(window.parent)
    window.parent.qingqiu();
    $(window.parent.document).contents().find('.bg').remove();
    $(window.parent.document).contents().find('.modalPop').remove();
}

function closeModal() {
    // window.parent.$('.modalPop,.bg').fadeOut('slow',function () {
    //     console.log(window.parent.$('.modalPop').remove())
    window.parent.$('.bg').remove();
    window.parent.$('.modalPop').remove();

    // })
}