// pages/xcx/xcx.js

const app = getApp()



Page({
//支付要得到的东西
  pay(e){
    var that = this;
    //下单
    wx.request({
      url: "http://192.168.6.16:8080/pay?id=" + e.currentTarget.dataset.id,
      
      //成功
      header:{
        token:wx.getStorageSync("token")
      },
      success:function(res){       
        wx.requestPayment({
          ...res.data.data, success: function (res) {
            console.info("支付成功")
            wx.showToast({
              title: '成功',
              icon: 'success',
            })
            that.getOrders()
          }, fail: function () {
            console.info("支付失败" + res.data.data)
          }
        })
      }
    })
    
    

  },
//退款
  refund(e) {
    var that = this
    let id = e.currentTarget.dataset.id;
    wx.request({
      url: 'http://192.168.6.16:8080/refund?id=' + e.currentTarget.dataset.id,
      
      header: {
        token: wx.getStorageSync("token")
      },
      success: res => {
        // if (res.data.code == 200) {
        //   that.data.orders.filter(o => o.id == id)[0].state = 2
        //   that.setData({ orders: this.data.orders });
        // }
        console.info("退款成功")
        wx.showLoading({
           title: '退款中',
           icon: 'success',
         })
        console.info(that.data.state);
        const temp = setInterval(() => {

          if (that.data.state) {
            console.info("成功");
            wx.hideLoading();
            that.getOrders();//获取订单
            wx.showToast({
              title: '退款成功',
              icon: 'success'
              
            })
            clearInterval(temp)
          }
          console.info("111111", that.data.state);
          wx.request({
           
            url: 'http://192.168.6.16:8080/orderxx/state?state=' + 2 + "&id=" + e.currentTarget.dataset.id,
            method: 'GET',
            success: (res) => {
              console.info("res", res)
              if (res.data.data == true) {
                this.data.state = true;
              }
            }
          })
          // that.queryStatus(2, e.currentTarget.dataset.id);
        }, 1000);
       
      }
    })
  },
  

//添加订单
  add(){
    wx.request({
      url: 'http://192.168.6.16:8080/orderxx',
      method: 'post',
      header:{
        token: wx.getStorageSync("token")
      },
      success: res => {
        this.getOrders()
      }
    })
  },
  //删除订单
  deletex(e) {
    console.info(e.currentTarget.dataset)

    wx.request({
      url: "http://192.168.6.16:8080/orderxx?id=" + e.currentTarget.dataset.id,
      method: "delete",
      header: {
        token: wx.getStorageSync("token")
      },
      success: res => {
        this.getOrders()

      }
    })
  },
  queryStatus(){

  },

  /**
   * 页面的初始数据
   */
  data: {
    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo'),
    orders:[],
    state: false
  },

  /**
   * 生命周期函数--监听页面加载
   */ 
  onLoad: function (options) {
    if (app.globalData.userInfo) {
      this.setData({
        userInfo: app.globalData.userInfo,
        hasUserInfo: true
      })
    } else if (this.data.canIUse) {
      // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
      // 所以此处加入 callback 以防止这种情况
      app.userInfoReadyCallback = res => {
        this.setData({
          userInfo: res.userInfo,
          hasUserInfo: true
        })
      }
    } else {
      // 在没有 open-type=getUserInfo 版本的兼容处理
      wx.getUserInfo({
        success: res => {
          app.globalData.userInfo = res.userInfo
          this.setData({
            userInfo: res.userInfo,
            hasUserInfo: true
          })
        }
      })
    }
  },
  getUserInfo: function (e) {
    console.log(e)
    app.globalData.userInfo = e.detail.userInfo
    this.setData({
      userInfo: e.detail.userInfo,
      hasUserInfo: true
    })
  },

  //获取全部订单
  getOrders: function () {
    wx.request({
      url: 'http://192.168.6.16:8080/orderxx',
      // method: "get",
      header: {
        token: wx.getStorageSync("token")
      },
      success: res => {
        console.info("orders",res)
        this.setData({
          orders: res.data.data
        })
        
      }
    })
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
      wx.login({
        success: res => {
          // 发送 res.code 到后台换取 openId, sessionKey, unionId
          wx.request({
            url: 'http://192.168.6.16:8080/dzm/auth',
            data: {
              code: res.code
            },
            success:(res) => {
              wx.setStorageSync("token", res.data.data)
              if(res.data.data){
                this.getOrders()
              }
            },

          })
        }
      })
  

  },
  
  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})