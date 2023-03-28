export interface IEvents {
  id: number;
  name?: string | null;
  startDate?: string | null;
  enDate?: string | null;
}

export type NewEvents = Omit<IEvents, 'id'> & { id: null };
