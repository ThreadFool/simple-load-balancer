package threadfool.op.logic;

public interface LoadBalancer {
	BackendNode select();
}
