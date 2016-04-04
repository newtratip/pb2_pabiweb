Ext.define('PB.button.LinkButton', {
    extend: 'Ext.Component',
    alias: 'widget.linkbutton',
    renderTpl: '<div id="{id}-btnWrap" style="min-height:16px;" class="{baseCls}-linkbutton">' +
    '<a id="{id}-btnEl" href="#" <tpl if="tabIndex"> tabIndex="{tabIndex}"</tpl> role="link">' +
    '<tpl if="iconCls">' + '<span style="min-width:16px;min-height:16px;float:left;" class="{iconCls}"></span>&nbsp;' + '</tpl>' +
    '<span id="{id}-btnInnerEl" class="{baseCls}-inner">' + '{text}' + '</span>' + '<span id="{id}-btnIconEl" class="{baseCls}-icon"></span>' + '</a>' + '</div>',
    renderSelectors: {
        linkEl: 'a'
    },
    initComponent: function () {
        this.callParent(arguments);
        this.renderData = {
            text: this.text,
            iconCls: this.iconCls
        };
    },
    listeners: {
        render: function (c) {
        	var me=this;
            if (me.tooltip) {
                me.setTooltip(me.tooltip, true);
            }
            c.el.on('click', c.handler);
        }
    },
    handler: function (e) {
    },
    setTooltip: function(tooltip, initial) {
        var me = this;

        if (me.rendered) {
            if (!initial || !tooltip) {
                me.clearTip();
            }
            if (tooltip) {
                if (Ext.quickTipsActive && Ext.isObject(tooltip)) {
                    Ext.tip.QuickTipManager.register(Ext.apply({
                        target: me.el.id
                    },
                    tooltip));
                    me.tooltip = tooltip;
                } else {
                    me.el.dom.setAttribute(me.getTipAttr(), tooltip);
                }
            }
        } else {
            me.tooltip = tooltip;
        }
        return me;
    },
    clearTip: function() {
        var me = this,
            el = me.el;

        if (Ext.quickTipsActive && Ext.isObject(me.tooltip)) {
            Ext.tip.QuickTipManager.unregister(el);
        } else {
            el.dom.removeAttribute(me.getTipAttr());
        }
    },
    getTipAttr: function(){
        return this.tooltipType == 'qtip' ? 'data-qtip' : 'title';
    },
    tooltipType: 'qtip'


});