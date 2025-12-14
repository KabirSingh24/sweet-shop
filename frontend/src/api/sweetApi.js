import api from "./axios";

export const fetchSweets = async () => {
  const res = await api.get("/sweets");
  return res.data;
};

export const searchSweets = async (params) => {
  const res = await api.get("/sweets/search", { params });
  return res.data;
};

export const purchaseSweet = async (id, quantity) => {
  const res = await api.post(
    `/sweets/${id}/purchase`,
    null,
    {
      params: { quantity }
    }
  );
  return res.data;
};

export const restockSweet = async (id, quantity) => {
  const res = await api.post(
    `/sweets/${id}/restock`,
    null,
    {
      params: { quantity }
    }
  );
  return res.data;
};


export const deleteSweet = async (id) => {
  await api.delete(`/sweets/${id}`);
};

export const addSweet = async (data) => {
  const res = await api.post("/sweets", data);
  return res.data;
};

export const updateSweet = async (id, data) => {
  const res = await api.put(`/sweets/${id}`, data);
  return res.data;
};
