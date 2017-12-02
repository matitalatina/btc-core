import { WS_CORE_ENDPOINT } from '../config'

class RateUpdateListener {
  constructor({ onUpdate, currencyCode = null }) {
    this.onUpdate = onUpdate
    this.currencyCode = currencyCode
  }

  listen(currencyCode) {
    this.currencyCode = currencyCode

    if (this.ws) {
      this.disconnect()
    }

    this.connect()

    return this
  }

  connect() {
    const ws = new WebSocket(`${WS_CORE_ENDPOINT}/bitcoins/rates/${this.currencyCode}/updates/`)
    ws.onmessage = this.updateMessage.bind(this)
    this.ws = ws
  }

  disconnect() {
    if (this.ws) {
      this.ws.close()
      this.ws = null
    }
  }

  updateMessage(message) {
    this.onUpdate(JSON.parse(message.data))
  }
}

export default RateUpdateListener
