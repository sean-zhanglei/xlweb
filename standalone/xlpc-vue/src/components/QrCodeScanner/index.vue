<template>
  <div>
    <div class="ku-scanner">
      <qrcode-stream
        v-show="qrcode"
        :camera="camera"
        :torch="torchActive"
        @decode="onDecode"
        @init="onInit"
      >
        <div class="ku-scanner-content">
          <div class="ku-scanner-tooltip">
            将二维码/条码放入框内，即自动扫描
          </div>
          <div class="ku-scanner-section">
            <div class="ku-scanner-section-animation-line"></div>
            <div class="ku-scanner-section-angle"></div>
          </div>
        </div>
      </qrcode-stream>
    </div>
    <div class="ku-scanner-error" style="white-space: nowrap;overflow: hidden;text-overflow: ellipsis;">错误信息:{{ error }}</div>
    <el-row :gutter="20" type="flex" justify="space-between">
      <el-col :span="12"><el-button size="small" type="primary" @click="openCamera">重新扫描</el-button></el-col>
      <el-col :span="12"><el-button size="small" type="info" @click="closeCamera">关闭扫描</el-button></el-col>
    </el-row>
    <el-row :gutter="20" type="flex" justify="space-between">
      <el-col :span="12"><el-button size="small" plain @click="openFlash">打开灯光</el-button></el-col>
      <el-col :span="12"><el-button size="small" type="warning" @click="switchCamera">相机反转</el-button></el-col>
    </el-row>
  </div>
</template>
<script>
// 引入
import { QrcodeStream } from "vue-qrcode-reader";

export default {
  name: "QrCodeScanner",
  // 注册
  components: {
    QrcodeStream
  },

  data() {
    return {
      result: "", // 扫码结果信息
      error: "", // 错误信息
      torchActive: false,
      qrcode: true,
      camera: "rear",
    };
  },

  methods: {
    onDecode(result) {
      console.log(result);
      this.result = result;
      // 使用 $emit 触发自定义事件，并传递值
      this.$emit('verify-code-event', this.result);
    },
    async onInit(promise) {
      const { capabilities } = await promise;

      const TORCH_IS_SUPPORTED = !!capabilities.torch;
      try {
        await promise;
      } catch (error) {
        if (error.name === "NotAllowedError") {
          this.error = "您需要授予相机访问权限";
        } else if (error.name === "NotFoundError") {
          this.error = "这个设备上没有摄像头";
        } else if (error.name === "NotSupportedError") {
          this.error = "此浏览器不支持";
        } else if (error.name === "NotReadableError") {
          this.error = "相机被占用";
        } else if (error.name === "OverconstrainedError") {
          this.error = "安装摄像头不合适";
        } else if (error.name === "StreamApiNotSupportedError") {
          this.error = "此浏览器不支持";
        } else {
          this.error = "此浏览器不支持";

        }
      }
    },
    // 打开相机
    openCamera() {
      this.camera = "off";
      this.qrcode = false;
      setTimeout(() => {
        this.camera = "rear";
        this.qrcode = true;
      }, 1000);

    },
    // 关闭相机
    closeCamera() {
      this.camera = "off";
      this.qrcode = false;
    },
    // 相机反转
    switchCamera() {
      // console.log(this.camera);
      switch (this.camera) {
        case "front":
          this.camera = "rear";
          console.log(this.camera);
          break;
        case "rear":
          this.camera = "front";
          console.log(this.camera);
          break;
      }
    },
    // 打开手电筒
    openFlash() {
      switch (this.torchActive) {
        case true:
          this.torchActive = false;
          break;
        case false:
          this.torchActive = true;
          break;
      }
    }
  }
};
</script>
<style scoped lang="scss">

.el-row {
  margin-bottom: 0.3rem;
}
.ku-scanner-error {
  font-weight: bold;
  font-size: 14px;
  color: red;
  padding: 0.2rem 0 0.2rem  0;
}
.ku-scanner-decode-result {
  font-size: 14px;
  color: #000;
}
.ku-scanner {
  height: 4rem;
  .ku-scanner-content {
    background-image: linear-gradient(
        0deg,
        transparent 24%,
        rgba(32, 255, 77, 0.1) 25%,
        rgba(32, 255, 77, 0.1) 26%,
        transparent 27%,
        transparent 74%,
        rgba(32, 255, 77, 0.1) 75%,
        rgba(32, 255, 77, 0.1) 76%,
        transparent 77%,
        transparent
      ),
      linear-gradient(
        90deg,
        transparent 24%,
        rgba(32, 255, 77, 0.1) 25%,
        rgba(32, 255, 77, 0.1) 26%,
        transparent 27%,
        transparent 74%,
        rgba(32, 255, 77, 0.1) 75%,
        rgba(32, 255, 77, 0.1) 76%,
        transparent 77%,
        transparent
      );
    background-size: 3rem 3rem;
    background-position: -1rem -1rem;
    width: 100%;
    height: 4rem;
    position: relative;
    background-color: rgba(18, 18, 18, 0);
    // 提示信息
    .ku-scanner-tooltip {
      width: 100%;
      height: 35px;
      line-height: 35px;
      font-size: 14px;
      text-align: center; /* color: #f9f9f9; */
      margin: 0 auto;
      position: absolute;
      top: 0;
      left: 0;
      color: #ffffff;
    }
    .ku-scanner-section {
      width: 4rem;
      height: 4rem;
      position: absolute;
      left: 50%;
      top: 50%;
      transform: translate(-50%, -50%);
      overflow: hidden;
      border: 0.1rem solid rgba(0, 255, 51, 0.2);
      // 装饰角
      &:after {
        content: "";
        display: block;
        position: absolute;
        width: 12px;
        height: 12px;
        border: 0.2rem solid transparent;
        top: 0;
        border-top-color: #00ff33;
        right: 0;
        border-right-color: #00ff33;
      }
      &:before {
        content: "";
        display: block;
        position: absolute;
        width: 12px;
        height: 12px;
        border: 0.2rem solid transparent;
        top: 0;
        border-top-color: #00ff33;
        left: 0;
        border-left-color: #00ff33;
      }
      // 装饰角
      .ku-scanner-section-angle {
        &:after {
          content: "";
          display: block;
          position: absolute;
          width: 12px;
          height: 12px;
          border: 0.2rem solid transparent;
          bottom: 0;
          border-bottom-color: #00ff33;
          right: 0;
          border-right-color: #00ff33;
        }
        &:before {
          content: "";
          display: block;
          position: absolute;
          width: 12px;
          height: 12px;
          border: 0.2rem solid transparent;
          bottom: 0;
          border-bottom-color: #00ff33;
          left: 0;
          border-left-color: #00ff33;
        }
      }
      // 扫描活动线
      .ku-scanner-section-animation-line {
        height: calc(100% - 2px);
        width: 100%;
        background: linear-gradient(
          180deg,
          rgba(0, 255, 51, 0) 43%,
          #00ff33 211%
        );
        border-bottom: 3px solid #00ff33;
        transform: translateY(-100%);
        animation: radar-beam 2s infinite alternate;
        animation-timing-function: cubic-bezier(0.53, 0, 0.43, 0.99);
        animation-delay: 1.4s;
      }
    }
  }
}
// 扫描活动线--上下 动画
@keyframes radar-beam {
  0% {
    transform: translateY(-100%);
  }

  100% {
    transform: translateY(0);
  }
}
</style>
