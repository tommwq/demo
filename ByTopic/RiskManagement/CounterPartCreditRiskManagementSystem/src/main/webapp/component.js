Vue.component("simple-input", {
    template: `
<div>
  <span>{{title}}</span>
  <input v-model="inputValue"/>
  <button v-on:click="$emit(eventname, inputValue)">{{buttontext}}</button>
</div>
`,
    data: function() {
        return {
            "inputValue": "",
        };
    },
    props: [
        "title",
        "buttontext",
        "eventname"
    ]
});
