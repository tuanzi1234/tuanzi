<script>
/**
 * 验证码组件
 * 生成并绘制验证码
 */
export default {
  name: "validCode",
  data() {
    return {
      codes: [], // 存储验证码字符
      ctx: "", // 画布上下文
      colors: ["red", "yellow", "blue", "green", "pink", "black"], // 可选颜色数组
      code_Len: 4 // 验证码长度
    };
  },
  mounted() {
    this.draw();
  },
  computed: {
    /**
     * 将验证码字符数组转换为字符串并大写
     * @returns {string} 大写的验证码字符串
     */
    codeString() {
      let result = "";
      for (let i = 0; i < this.codes.length; i++) {
        result += this.codes[i];
      }
      return result.toUpperCase();
    }
  },
  watch: {
    /**
     * 监听验证码字符串变化，当变化时触发父组件的change事件
     * @param {string} newValue 新的验证码字符串
     */
    codeString: function(newValue) {
      this.$emit("change", newValue);
    }
  },
  methods: {
    /**
     * 生成随机验证码字符数组
     */
    generateRandom() {
      this.codes = [];
      const chars = "abcdefghijkmnpqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ23456789";
      const charsArr = chars.split("");

      for (let i = 0; i < this.code_Len; i++) {
        const num = Math.floor(Math.random() * charsArr.length);
        this.codes.push(charsArr[num]);
      }
    },
    /**
     * 绘制验证码
     */
    draw() {
      this.generateRandom();
      this.drawText();
    },
    /**
     * 绘制干扰线
     */
    drawLine() {
      const lineNumber = 3; // 线条条数
      const lineX = 140;
      const lineY = 30; // 最大线条坐标
      for (let i = 0; i < lineNumber; i++) {
        this.ctx.strokeStyle = this.colors[Math.floor(Math.random() * 5)];
        this.ctx.beginPath();
        this.ctx.moveTo(
          Math.floor(Math.random() * lineX),
          Math.floor(Math.random() * lineY)
        );
        this.ctx.lineTo(
          Math.floor(Math.random() * lineX),
          Math.floor(Math.random() * lineY)
        );
        this.ctx.stroke();
      }
    },
    /**
     * 在画布上绘制验证码和背景
     */
    drawText() {
      const canvas = this.$refs["canvas"];
      this.ctx = canvas.getContext("2d");

      this.ctx.fillStyle = "#BFEFFF";
      this.ctx.fillRect(0, 0, 140, 40);
      this.ctx.font = "20px Verdana";

      let x = 15;

      for (let i = 0; i < this.code_Len; i++) {
        this.ctx.fillStyle = this.colors[Math.floor(Math.random() * 5)];
        this.ctx.fillText(this.codes[i], x, 25);
        x = x + 30;
      }

      this.drawLine();
    }
  }
};
</script>

<template>
  <!-- 点击时重新绘制验证码的画布 -->
  <canvas ref="canvas" @click="draw" width="140" height="40" style="cursor: pointer;"></canvas>
</template>