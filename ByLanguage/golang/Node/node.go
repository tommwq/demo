package main

import (
	"log"
)

type Node struct {
	processor Processor
	input     chan *Payload
	output    chan *Payload
	manager   *NodeManager
	job       string
}

func (r *Node) Post(p *Payload) {
	r.input <- p
}

func (r *Node) Get() *Payload {
	return <-r.output
}

func (r *Node) Initialize(param interface{}) {
	r.processor.Initialize(param)
}

func (r *Node) Start() {
	log.Printf("开始启动<%v>节点<%p>。\n", r.job, r)
	go func() {
		for {
			r.manager.onNodeReady(r.job, r)
			select {
			case p := <-r.input:
				r.processor.Process(p)
			}
		}
	}()
}

func (r *Node) Stop() {
}

type NodeManager struct {
	scheduleQueue map[string]chan *Node
	nodes         map[string][]*Node
}

func NewNodeManager() *NodeManager {
	return &NodeManager{
		scheduleQueue: make(map[string]chan *Node),
		nodes:         make(map[string][]*Node),
	}
}

func (r *NodeManager) StartNode(job string, number int, processor Processor) {
	log.Printf("开始启动<%v>节点，数量%v。\n", job, number)

	r.scheduleQueue[job] = make(chan *Node, number)
	r.nodes[job] = make([]*Node, 0, number)

	for i := 0; i < number; i++ {
		n := r.createNode(job, processor)
		n.Start()
	}
	log.Printf("启动<%v>节点，数量%v完毕。\n", job, number)
}

func (r *NodeManager) createNode(job string, processor Processor) *Node {
	log.Printf("开始建立<%v>节点。\n", job)
	n := &Node{
		input:     make(chan *Payload, 4096),
		output:    make(chan *Payload, 4096),
		manager:   r,
		job:       job,
		processor: processor,
	}

	r.nodes[job] = append(r.nodes[job], n)
	log.Printf("<%v>建立节点完毕。\n", job)
	return n
}

func (r *NodeManager) Post(job string, p *Payload) {
	nodes, ok := r.scheduleQueue[job]
	if !ok {
		log.Printf("警告：无法识别的任务<%v>。\n", job)
		return
	}
	go func() {
		n := <-nodes
		n.Post(p)
	}()
}

func (r *NodeManager) onNodeReady(job string, node *Node) {
	nodes, ok := r.scheduleQueue[job]
	if !ok {
		log.Printf("警告：无法识别的任务<%v>。\n", job)
		return
	}
	nodes <- node
}
